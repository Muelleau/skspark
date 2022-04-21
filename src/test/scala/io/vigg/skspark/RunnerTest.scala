package io.vigg.skgraph

import io.vigg.skspark.Runner
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(classOf[JUnit4])
class RunnerTest {

  @Before def initialize(): Unit = {}

  @Test def testGraphImplementation(): Unit = Runner.main(Array("test"))

}
