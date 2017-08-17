package util

import javax.inject.{Inject, Singleton}

import play.api.Configuration

@Singleton
class Auth0 @Inject() (config: Configuration) {
  val domain = config.get[String]("auth0.domain")

  val clientId = config.get[String]("auth0.clientId")

  val clientSecret = config.get[String]("auth0.clientSecret")

  val callbackUrl = config.get[String]("auth0.callbackUrl")
}
