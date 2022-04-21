package io.vigg.skspark

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait SparkApplication extends SkApplication {

  implicit val spark: SparkSession = getSparkSession(new SparkConf())

  def getSparkSession(sparkConf: SparkConf): SparkSession = {
    if (Constants.MODE.equals("LOCAL")) {
      SparkSession.builder().config(sparkConf).master("local").getOrCreate()
    }
    SparkSession.builder().config(sparkConf).getOrCreate()
  }

}
