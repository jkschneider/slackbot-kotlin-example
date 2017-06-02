package io.pivotal.dublin.beach

import junit.framework.Assert.assertTrue
import me.ramswaroop.jbot.core.slack.SlackService
import me.ramswaroop.jbot.core.slack.models.User
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.rule.OutputCapture
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession


@RunWith(MockitoJUnitRunner::class)
@ActiveProfiles("test")
@SpringBootTest
class SlackBotTest {

    @Mock
    lateinit var session: WebSocketSession

    @Mock
    lateinit var slackService: SlackService

    @Mock
    lateinit private var env: Environment

    @InjectMocks
    lateinit var slackBot: SlackBot

    @Rule @JvmField
    var capture = OutputCapture()

    @Before
    fun setUp() {
        val user = User()
        user.name = "test bot"
        user.id = "PVTLBOTID"
        `when`(slackService.currentUser).thenReturn(user)
    }


    @Test
    fun when_DirectMessage_NotRecognized_Should_RepondNotRecognizedCommand() {
        // At this point the Mock for slackService has been created, but it will not be injected
        // into the slackBot instance step inside to verify
        slackBot.handleTextMessage(session, buildTextMessage())

        val response = capture.toString()
        assertTrue(response == "Your command has not been recognized. Couldn't process your query")
    }

    fun buildTextMessage(): TextMessage {
        return TextMessage("{\"type\": \"message\"," +
                "\"ts\": \"1358878749.000002\"," +
                "\"user\": \"U023BECGF\"," +
                "\"text\": \"<@UEADGH12S>:\"}")
    }
}