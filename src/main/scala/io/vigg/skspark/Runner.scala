package io.vigg.skspark

object Runner {

  def matchApp[T <: SkApplication](
      args: Array[String]
  ): SkApplication = {

    case "test" => {
      new SparkJob {
        override def run: Unit = println("run test")
        override def setConf: Unit = {}
      }
    }
    case _ =>
      println(job)
      throw new Exception("invalid job id provided")

  }

  def main(args: Array[String]): Unit = matchApp(args).launch(args)

}
