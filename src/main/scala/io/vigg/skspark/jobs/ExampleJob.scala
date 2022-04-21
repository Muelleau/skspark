package io.vigg.skspark.jobs

import io.vigg.skspark.SparkJob

class ExampleJob extends SparkJob {

  override def run(): Unit = {
    println("implement me!!!")
  }

  override def setConf(): Unit = {
    println("setting my configuration")
  }

}
