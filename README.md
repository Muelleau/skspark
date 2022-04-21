
# SkSpark

_pronunciation: (es-kay-spark)_

SkSpark is a skeleton implementation of a Spark job runner platform. It provides an easy
to use interface for creating and running Scala tasks, and can be coordinated via remote
calls to run it's implemented tasks.

The main purpose of this library is to enable any non-ETL / Spark / ML / Data Engineers to author
their own Spark tasks without needing to worry about the implementation details of Spark itself.

There is a hierarchy of primitives available in the library. The top level trait is the SkApplication,
which mandates the implementation of a launch command (implementation details have 
been removed for brevity).

``` 
trait SkApplication {
  def launch(args: Array[String]): Unit
}
```
The library user may create SkApplications as standalone tasks, or extend it via the `SparkApplication`
trait:

```
trait SparkApplication extends SkApplication {

  implicit val spark: SparkSession = getSparkSession(new SparkConf())
  
  def getSparkSession(sparkConf: SparkConf): SparkSession = {
    if(Constants.MODE.equals("LOCAL")) {
      SparkSession.builder().config(sparkConf).master("local").getOrCreate()
    }
    SparkSession.builder().config(sparkConf).getOrCreate()
  }
  
}
```

This provides access to a Spark context where you may further coordinate and implement your jobs. From 
there, the most basic implementation is the `SparkJob`:

``` 
trait SparkJob extends SparkApplication {
  
  def run(): Unit
  def setConf(): Unit

  def launch(args: Array[String]): Unit = {
    spark.sparkContext.setLogLevel("ERROR")
    setConf()
    run()
  }

  def getBigQuery(sql: String): DataFrame = {
    spark.read
      .format("bigquery")
      .load(sql)
  }

  def writeBigQuery(df: DataFrame, table: String, mode: SaveMode): Unit =
    df.write
      .format("bigquery")
      .mode(mode)
      .option("table", table)
      .save()

  def getJDBC(sql: String): DataFrame = {
    spark.read
      .format("jdbc")
      .option("dbtable", sql)
      .option("url", JDBC_URL)
      .option("driver", JDBC_DRIVER)
      .option("user", JDBC_USER)
      .option("password", JDBC_PASSWORD)
      .load()
  }

  def writeJDBC(df: DataFrame, table: String, mode: SaveMode): Unit = {
    df.write
      .format("jdbc")
      .mode(mode)
      .option("url", JDBC_URL)
      .option("driver", JDBC_DRIVER)
      .option("user", JDBC_USER)
      .option("password", JDBC_PASSWORD)
      .option("dbtable", table)
      .save()
  }

  def saveDf(df: DataFrame, path: String, mode: SaveMode): Unit =
    df.write.mode(mode).parquet(path)

  def saveModel(model: MLWritable, path: String): Unit =
    model.write.overwrite().save(path)

}
```

This gives you a way to set the Spark configuration for your task, define the job itself via the `run` method,
and numerous utilities for accessing data and saving models.

