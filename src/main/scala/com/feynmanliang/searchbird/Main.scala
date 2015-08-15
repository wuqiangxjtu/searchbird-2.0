package com.feynmanliang.searchbird

import com.feynmanliang.searchbird.config.SearchbirdServiceConfig
import com.twitter.finagle.Thrift
import com.twitter.server.TwitterServer
import com.twitter.util.Await

/**
 * Created by fliang on 8/15/15.
 */
object Main extends TwitterServer {
  def main(): Unit = {
    val config = new SearchbirdServiceConfig()
    val service = new SearchbirdServiceImpl(config)
    val server = Thrift
      .serveIface("localhost:" + config.thriftPort, service)

    onExit {
      server.close()
      adminHttpServer.close()
    }
    Await.ready(server)
    Await.ready(adminHttpServer)
  }
}