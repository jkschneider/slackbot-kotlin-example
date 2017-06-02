package io.pivotal.dublin.beach

import me.ramswaroop.jbot.core.slack.SlackService
import me.ramswaroop.jbot.core.slack.models.User
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.rule.OutputCapture
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession


@ActiveProfiles("test")
@SpringBootTest
class SlackBotTest {

    @Mock
    lateinit var session: WebSocketSession

    @Mock
    lateinit var slackService: SlackService

    @InjectMocks
    lateinit var slackBot: SlackBot

    @Rule @JvmField
    val capture = OutputCapture()

    @Before
    fun setUp() {
        // Because Mockito will only do EITHER constructor or field injection, we have to take the need for
        // constructor injection off the table before Mockito scans for annotations.
        // see: https://mhaligowski.github.io/blog/2014/05/30/mockito-with-both-constructor-and-field-injection.html
        slackBot = SlackBot(mock(Environment::class.java))
        MockitoAnnotations.initMocks(this)

        val user = User()
        user.name = "test bot"
        user.id = "PVTLBOTID"
        `when`(slackService.currentUser).thenReturn(user)
    }

    @Test
    fun when_DirectMessage_NotRecognized_Should_RepondNotRecognizedCommand() {
        // slackService has been injected into slackBot
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