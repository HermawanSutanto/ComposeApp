package com.dicoding.listcompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.dicoding.listcompose.model.HeroRepository
import com.dicoding.listcompose.ui.DetailObject
import com.dicoding.listcompose.ui.ListComposeAppTheme
import kotlinx.coroutines.launch
import com.dicoding.listcompose.ui.Navigation.NavigationItem
import com.dicoding.listcompose.ui.Navigation.Screen


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListComposeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ListComposeViewModel = viewModel(factory = ViewModelFactory(HeroRepository())),
    navigateToDetail: (String?) -> Unit

) {

    val groupedHeroes by viewModel.groupedHeroes.collectAsState()
    val query by viewModel.query

    Box(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }

        }
        Scaffold(
            bottomBar = {
                if (currentRoute == Screen.Home.route||currentRoute == Screen.About.route) {
                    BottomBar(navController)
                }
            },
            modifier = modifier
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {

                        item {
                            SearchBar(
                                query = query,
                                onQueryChange = viewModel::search,
                                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                            )

                        }

                        groupedHeroes.forEach { (initial, heroes) ->
                            stickyHeader {
                                CharacterHeader(initial)
                            }
                            items(heroes, key = { it.id }) { hero ->
                                HeroListItem(
                                    name = hero.name,
                                    photoUrl = hero.photoUrl,
                                    description = hero.description,
                                    genre = hero.genre,
                                    modifier = Modifier,
                                    onClick = { navigateToDetail(hero.id) }
                                )
                            }
                        }
                    }
                    AnimatedVisibility(
                        visible = showButton,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically(),
                        modifier = Modifier
                            .padding(bottom = 30.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        ScrollToTopButton(
                            onClick = {
                                scope.launch {
                                    listState.scrollToItem(index = 0)
                                }
                            }
                        )
                    }
                }
                composable(Screen.About.route) {
                    About()
                }
                composable(
                    route = "${Screen.DetailObject.route}/{heroId}",
                    arguments = listOf(navArgument("heroId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val arguments = requireNotNull(backStackEntry.arguments)
                    val heroId = arguments.getString("heroId")
                    DetailObject(heroId)
                }
            }

        }


    }
}
@Preview(showBackground = true)
@Composable
fun ListComposeAppPreview() {
    val navigateToDetail: ((String?) -> Unit)
            = "" as ((String?) -> Unit)
    ListComposeAppTheme {
        ListComposeApp(navigateToDetail = navigateToDetail)
    }
}

@Composable
fun CharacterHeader(
    char: Char,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Text(
            text = char.toString(),
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Composable
fun HeroListItem(
    name: String,
    photoUrl: String,
    description:String,
    genre: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun HeroListItemPreview() {
    ListComposeAppTheme {
        HeroListItem(
            name = "H.O.S. Cokroaminoto",
            photoUrl = "",
            description = "ini game",
            genre = "aksi",
            onClick = {}
        )
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.search_hero))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {
    }
}
@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_about),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

