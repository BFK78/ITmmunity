package com.brainer.itmmunity.Componant

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brainer.itmmunity.ContentView
import com.brainer.itmmunity.Croll.Croll
import com.brainer.itmmunity.R
import com.google.accompanist.glide.rememberGlidePainter
import kotlinx.coroutines.DelicateCoroutinesApi


@Preview
@Composable
fun LoadingView() {
    Column {
        Spacer(modifier = Modifier.weight(10f))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), text = "로딩중입니다... ", fontSize = 20.sp, textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(10f))
    }
}

//@Composable
//fun FilMaxBackGround() {
//    if (isSystemInDarkTheme()) {
//        Surface(Modifier.fillMaxSize(), color = com.brainer.ITmmunity.ui.theme.themeOFDarkPrimary) {
//        }
//    } else if (!isSystemInDarkTheme()) {
//        Surface(Modifier.fillMaxSize(), color = com.brainer.ITmmunity.ui.theme.Primary) {
//        }
//    }
//}

@Composable
fun AppBar() {
    val scaffoldState = rememberScaffoldState()
    Log.i("Simple Log", "scaffoldState: $scaffoldState")
    Column {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = { Text("Drawer content") },
//            topBar = {
//                TopAppBar(
//                    title = { Text("Simple Scaffold Screen") },
//                    navigationIcon = {
//                        IconButton(
//                            onClick = {
////                                scope.launch { scaffoldState.drawerState.open() }
//                            }
//                        ) {
//                            Icon(Icons.Filled.Menu, contentDescription = "Localized description")
//                        }
//                    }
//                )
//            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Inc") },
                    onClick = { /* fab click handler */ }
                )
            },
            content = { innerPadding ->
                LazyColumn(contentPadding = innerPadding) {
                    items(100) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(50.dp)
//                                .background(colors[it % colors.size])
                        )
                    }
                }
            }
        )
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun NewsCard(
    news: List<Croll.Content>
) {
    var backGroundUnitColor: Color = Color(255, 255, 255)
    if (isSystemInDarkTheme()) {
        backGroundUnitColor = Color(23, 23, 23)
    } else if (!isSystemInDarkTheme()) {
        backGroundUnitColor = Color(255, 255, 255)
    }

    val listState = rememberLazyListState()
    Surface(shape = RoundedCornerShape(25.dp)) {
        LazyColumn(state = listState) {
            items(news) { item ->
                NewsListOf(item)
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@DelicateCoroutinesApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NewsListOf(aNews: Croll.Content, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var contentHtml: String?

    Column {
        Surface(
            Modifier
                .height(125.dp)
                .fillMaxWidth()
                .background(Color.White)
                .clickable { expanded = !expanded }) {
            Row(modifier = modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                if (aNews.image != null) {
                    Log.i("Thumbnail", "Thumbnail load success")
                    Log.d("Thumbnail", "Thumbnail: " + aNews.image)
                    Image(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp)),
                        painter = rememberGlidePainter(
                            request = aNews.image,
                        ),
                        contentDescription = stringResource(R.string.main_thumbnail),
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f), text = aNews.title, style = MaterialTheme.typography.h6
                )
            }

//        Row {
//            Text(text = "조회수: " +aNews.hit.toString(), style = MaterialTheme.typography.body1)
//            Spacer(modifier = Modifier.padding(2.dp))
//            Text(text = aNews.numComment.toString(), style = MaterialTheme.typography.body1)
//        }
//        Divider(Modifier.padding(top = 12.dp, bottom = 0.dp))
        }

        if (expanded) {
            val context = LocalContext.current
            val contentIntent = Intent(Intent(LocalContext.current, ContentView::class.java))

            contentIntent.putExtra("content", aNews)
            context.startActivity(contentIntent)
            expanded = !expanded
        }
    }
}


//@Composable
//fun DrawUnderKGNews(doc: String?) {
//    if (doc != null) {
//        Text(modifier = Modifier
////        .clickable { expanded = !expanded }
//            .fillMaxWidth(), text = doc, textAlign = TextAlign.Center)
//    }
//}

@Composable
fun NewsItem(item: Croll.Content) {
    Text(text = item.url)
}