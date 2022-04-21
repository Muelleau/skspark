package io.vigg.skspark

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.SparkConf
import Constants._
import org.apache.spark.ml.util.MLWritable

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
