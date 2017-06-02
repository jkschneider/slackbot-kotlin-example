package io.pivotal.dublin.beach

import me.ramswaroop.jbot.core.slack.Bot
import me.ramswaroop.jbot.core.slack.Controller
import me.ramswaroop.jbot.core.slack.EventType
import me.ramswaroop.jbot.core.slack.models.Event
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession

@Component
class SlackBot(private val env: Environment) : Bot() {

    private val LOG = LoggerFactory.getLogger(SlackBot::class.java)

     override fun getSlackToken(): String {
        val slackToken = env.getProperty("slackBotToken")
        LOG.trace("getting slack token : $slackToken")
        println("STANDUP")
        return slackToken
    }

    override fun getSlackBot(): Bot {
        return this
    }

    @Controller(events = arrayOf(EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE), pattern = "(match me)")
    fun onMessageMatchMe(session: WebSocketSession, event: Event) {
        println("hey")
    }

    @Controller(events = arrayOf(EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE))
    fun onMessage(session: WebSocketSession, event: Event) {
        println("hey bad")
    }

}