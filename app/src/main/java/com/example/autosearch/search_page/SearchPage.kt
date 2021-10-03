package com.example.autosearch.search_page

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.sp
import com.example.autosearch.ui.theme.AutoSearchTheme

enum class ContentTypes(val type: String) {

    PHOTOS("Фотографии"),
    MESSAGES("Сообщения"),
    VIDEOS("Видео")
}

@Composable
fun DarkModeButton() {

    var isDarkModeOn: Boolean by remember { mutableStateOf(false) }

    Button(
        onClick = {
            isDarkModeOn = !isDarkModeOn
        },
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
    ) {

    }
}

@Composable
fun SearchTextField() {

    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    var text by rememberSaveable { mutableStateOf("") }
    var label: String? by remember { mutableStateOf("VIN номер авто") }

    TextField(
        value = text,
        onValueChange = { newValue ->
            text = newValue.uppercase()
        },
        singleLine = true,
        modifier = Modifier
            .padding(top = 12.dp)
            .onFocusEvent {
                if (it.isFocused) label = null
            },
        label = label?.let { { Text(it) } },
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {
            Icon(
                Icons.Filled.Search,
                "",
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    Toast.makeText(context, "Введите текст!", Toast.LENGTH_SHORT)
                        .show()
                }
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun DescriptionDivider(description: String, modifier: Modifier? = null) {

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier ?: Modifier.padding(top = 12.dp)
    ) {

        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = description,
            fontSize = 23.sp,
            color = MaterialTheme.colors.secondaryVariant
        )

        Spacer(modifier = Modifier.height(5.dp))

        Divider(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            color = MaterialTheme.colors.primary,
            thickness = 1.dp
        )
    }
}

@Composable
private fun ContentCard(contentType: ContentTypes, content: (() -> Unit)? = null) {

    DescriptionDivider(description = contentType.type, modifier = Modifier.padding(top = 12.dp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 324.dp)
            .padding(start = 50.dp, end = 50.dp, top = 12.dp, bottom = 12.dp),
        backgroundColor = MaterialTheme.colors.secondary
    ) {

        content?.invoke()

        //какой-нибудь when(contentType) { }
    }
}

@Composable
fun ContentPhotos() {

    ContentCard(contentType = ContentTypes.PHOTOS)
}

@Composable
fun ContentMessages() {

    ContentCard(contentType = ContentTypes.MESSAGES)
}

@Composable
fun ContentVideos() {

    ContentCard(contentType = ContentTypes.VIDEOS)
}
