package com.example

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.*
import com.example.ui.*
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val bcmViewModel: BcmViewModel = viewModel()
            val isDark by bcmViewModel.isDarkMode.collectAsState()

            MyApplicationTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BcmAppShell(viewModel = bcmViewModel)
                }
            }
        }
    }
}

// BILINGUAL TRANSLATOR DICTIONARY
fun getTxt(key: String, lang: String): String {
    val enMap = mapOf(
        "app_title" to "BCM GROUP",
        "tagline" to "Crafting Premium Spaces",
        "plots" to "Plots",
        "villas" to "Villas",
        "flats" to "Flats",
        "all" to "All Listings",
        "search_hint" to "Search projects, segments, or locations...",
        "categories" to "Choose Segments",
        "locations" to "Prime Unit Locations",
        "pali_projects" to "Pali Projects Unit",
        "projects_list" to "Featured Real Estates",
        "inquire_now" to "Send Free Inquiry",
        "whats_app_msg" to "Hello BCM GROUP, I am interested in your premier properties. Please share brochures.",
        "submit" to "Submit Inquiry",
        "name" to "Your Full Name *",
        "phone" to "Mobile / WhatsApp Number *",
        "email" to "Email Address (Optional)",
        "property" to "Property Typology",
        "budget" to "Budget Allocation Range",
        "message" to "Additional Requirements",
        "success" to "Inquiry Lodged!",
        "success_msg" to "Thank you for contacting BCM GROUP agents. A dedicated sales manager will reach out within 24 hours.",
        "call_agent" to "Direct Call Center",
        "active_inquiries" to "Local Enquiries History",
        "no_inquiries" to "No local search or inquiries lodged yet.",
        "home" to "Home",
        "pali" to "Pali Unit",
        "contact_us" to "Contact & Office",
        "office" to "Corporate Headquarters",
        "office_address" to "BCM Tower, Main Bandha Road, Near Pali Ring Circle, Rajasthan, PIN-306401",
        "close" to "Back",
        "back" to "Return",
        "explore_more" to "Inquire & Explore Project",
        "size" to "Property Plot Area",
        "price" to "Investment Estimates",
        "status" to "RERA Approved Stage",
        "all_locations" to "Click a location icon below to view city specific projects:"
    )

    val hiMap = mapOf(
        "app_title" to "बी.सी.एम. ग्रुप",
        "tagline" to "प्रीमियम विला और भूखंड",
        "plots" to "प्लॉट्स (भूखंड)",
        "villas" to "विला (कोठी)",
        "flats" to "फ्लैट्स (अपार्टमेंट)",
        "all" to "सभी संपत्तियां",
        "search_hint" to "प्रोजेक्ट्स, संपत्ति, शहरों की खोज करें...",
        "categories" to "हमारे प्रॉपर्टी सेगमेंट",
        "locations" to "प्रमुख लोकेशन्स",
        "pali_projects" to "पाली यूनिट प्रोजेक्ट्स",
        "projects_list" to "मुख्य प्रोजेक्ट्स",
        "inquire_now" to "पूछताछ शुरू करें",
        "whats_app_msg" to "नमस्ते बीसीएम ग्रुप, मैं आपकी प्राइम प्रॉपर्टीज में रुचि रखता हूं। कृपया मुझे विवरण साझा करें।",
        "submit" to "पूछताछ सबमिट करें",
        "name" to "आपका पूरा नाम *",
        "phone" to "मोबाइल / व्हाट्सएप नंबर *",
        "email" to "ईमेल (वैकल्पिक)",
        "property" to "संपत्ति श्रेणी",
        "budget" to "अनुकूल बजट सीमा",
        "message" to "कोई अन्य जानकारी",
        "success" to "सफलतापूर्वक सबमिट!",
        "success_msg" to "बीसीसीएम ग्रुप से संपर्क करने के लिए धन्यवाद। हमारे प्रतिनिधि 24 घंटे में आपके पास कॉल करेंगे।",
        "call_agent" to "कॉल सपोर्ट सेंटर",
        "active_inquiries" to "दर्ज पूछताछ इतिहास",
        "no_inquiries" to "अभी तक कोई स्थानीय पूछताछ दर्ज नहीं है।",
        "home" to "होम",
        "pali" to "पाली यूनिट",
        "contact_us" to "मुख्यालय संपर्क",
        "office" to "मुख्य कार्यालय पता",
        "office_address" to "बीसीसीएम टावर, मुख्य बांधा मार्ग, पाली रिंग चौराहा, राजस्थान, पिन-306401",
        "close" to "बंद करें",
        "back" to "पीछे जाएं",
        "explore_more" to "प्रोजेक्ट पूछताछ और विवरण",
        "size" to "प्रॉपर्टी क्षेत्रफल",
        "price" to "निवेश अनुमान मूल्य",
        "status" to "रेरा (RERA) स्थिति",
        "all_locations" to "शहर के अनुसार प्रोजेक्ट्स देखने के लिए नीचे क्लिक करें:"
    )

    return if (lang == "HI") {
        hiMap[key] ?: (enMap[key] ?: key)
    } else {
        enMap[key] ?: key
    }
}

// PREMIUM COIL ASYNC IMAGE LOADER WITH VECTOR GEOMETRIC FALLBACK
@Composable
fun BcmAsyncImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    cityInitials: String = "BCM"
) {
    val context = LocalContext.current
    var isError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize(),
            onError = {
                isError = true
                isLoading = false
            },
            onSuccess = {
                isLoading = false
            }
        )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(BcmDarkGreen, BcmDarkGreenLight)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = BcmGold,
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            }
        }

        if (isError) {
            // High-end minimalist designer backup card
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(BcmDarkGreen, BcmDarkGreenLight)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = cityInitials.uppercase(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = BcmGold,
                            letterSpacing = 3.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp, 1.5.dp)
                            .background(BcmGold)
                    )
                }
            }
        }
    }
}

@Composable
fun BcmAppShell(viewModel: BcmViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val currentStack by viewModel.navigationStack.collectAsState()
    val activeTabIndex by viewModel.activeTab.collectAsState()
    val isHiSelected by viewModel.language.collectAsState()

    // Retrieve dropdown state
    val plotsExpanded by viewModel.isPlotsDropdownExpanded.collectAsState()
    val villasExpanded by viewModel.isVillasDropdownExpanded.collectAsState()
    val flatsExpanded by viewModel.isFlatsDropdownExpanded.collectAsState()

    val currentScreen = currentStack.lastOrNull() ?: BcmScreen.Home

    // Intercept back actions
    androidx.activity.compose.BackHandler(enabled = currentStack.size > 1) {
        viewModel.navigateBack()
    }

    // Modal navigation drawer structure
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = BcmDarkBg,
                modifier = Modifier.width(310.dp)
            ) {
                // Sidebar Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(BcmDarkGreen, BcmDarkBg)
                            )
                        )
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                ) {
                    Column {
                        Text(
                            text = getTxt("app_title", isHiSelected),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = BcmGold,
                                letterSpacing = 2.sp
                            )
                        )
                        Text(
                            text = getTxt("tagline", isHiSelected),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = BcmGoldLight.copy(alpha = 0.8f),
                                letterSpacing = 1.sp
                            )
                        )
                    }
                }

                Divider(color = BcmGold.copy(alpha = 0.3f), thickness = 1.dp)

                // Scrollable Submenus
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // PLOTS SECTION
                    SidebarSection(
                        title = getTxt("plots", isHiSelected).uppercase(),
                        isExpanded = plotsExpanded,
                        onHeaderClick = { viewModel.togglePlotsDropdown() }
                    ) {
                        val plotsSub = listOf("Pali", "Jalore", "Sumerpur", "Sheoganj", "Sojat", "Marwar Jn.", "Takhatgarh", "Somesar")
                        plotsSub.forEach { city ->
                            DropdownMenuItemText(
                                label = city,
                                onClick = {
                                    coroutineScope.launch { drawerState.close() }
                                    viewModel.navigateTo(BcmScreen.CityProjects(city))
                                }
                            )
                        }
                    }

                    // VILLAS SECTION
                    SidebarSection(
                        title = getTxt("villas", isHiSelected).uppercase(),
                        isExpanded = villasExpanded,
                        onHeaderClick = { viewModel.toggleVillasDropdown() }
                    ) {
                        val villasSub = listOf("Pali", "Sheoganj", "Jalore", "Sojat")
                        villasSub.forEach { city ->
                            DropdownMenuItemText(
                                label = city,
                                onClick = {
                                    coroutineScope.launch { drawerState.close() }
                                    viewModel.navigateTo(BcmScreen.CityProjects(city))
                                }
                            )
                        }
                    }

                    // FLATS SECTION
                    SidebarSection(
                        title = getTxt("flats", isHiSelected).uppercase(),
                        isExpanded = flatsExpanded,
                        onHeaderClick = { viewModel.toggleFlatsDropdown() }
                    ) {
                        val flatsProjects = listOf(
                            "BCM Aashiyana Pali" to "bcm_aashiyana_pali",
                            "Vrindavan Heights Sheoganj" to "vrindavan_heights_sheoganj",
                            "BCM Aashiyana Marwar" to "bcm_aashiyana_marwar"
                        )
                        flatsProjects.forEach { (name, id) ->
                            DropdownMenuItemText(
                                label = name,
                                onClick = {
                                    coroutineScope.launch { drawerState.close() }
                                    val projectObj = BcmProjectDataProvider.projects.firstOrNull { it.id == id }
                                    if (projectObj != null) {
                                        viewModel.navigateTo(BcmScreen.ProjectDetail(projectObj))
                                    } else {
                                        viewModel.navigateTo(BcmScreen.CityProjects("Pali"))
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Drawer footer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "BCM GROUP © 2026",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = BcmGold.copy(alpha = 0.5f)
                            )
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                Column {
                    // THIN TOP RIBBON BAR / HEADER
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(BcmDarkGreen, BcmDarkGreenLight)
                                )
                            )
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Hamburger Left
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                },
                                modifier = Modifier.testTag("hamburger_menu_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Drawer menu",
                                    tint = BcmGold
                                )
                            }

                            // Center Text "BCM GROUP"
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = getTxt("app_title", isHiSelected),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = BcmGold,
                                        letterSpacing = 2.5.sp
                                    ),
                                    modifier = Modifier.clickable {
                                        viewModel.navigateTo(BcmScreen.Home)
                                    }
                                )
                                Text(
                                    text = getTxt("tagline", isHiSelected),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        color = BcmGoldLight.copy(alpha = 0.7f),
                                        letterSpacing = 1.sp
                                    )
                                )
                            }

                            // Left Options (Language EN | हिन्दी Toggle & Dark toggle)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                // Theme toggle
                                IconButton(
                                    onClick = { viewModel.toggleDarkMode() }
                                ) {
                                    val modeIcon = if (viewModel.isDarkMode.value) Icons.Default.LightMode else Icons.Default.DarkMode
                                    Icon(
                                        imageVector = modeIcon,
                                        contentDescription = "Toggle Theme",
                                        tint = BcmGold
                                    )
                                }

                                // Bilingual Toggle Buttton
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, BcmGold.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                                        .background(BcmDarkGreen)
                                        .clickable {
                                            viewModel.setLanguage(if (isHiSelected == "EN") "HI" else "EN")
                                        }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = if (isHiSelected == "EN") "हिन्दी" else "EN",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = BcmGold,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                    // Small Divider line underneath
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.5.dp)
                            .background(BcmGold)
                    )
                }
            },
            bottomBar = {
                // Sleek Material 3 Bottom Navigation bar with consistent pills & active states
                NavigationBar(
                    containerColor = if (viewModel.isDarkMode.value) BcmDarkBg else BcmDarkGreen,
                    tonalElevation = 8.dp,
                    windowInsets = WindowInsets.navigationBars
                ) {
                    val activeColor = BcmGold
                    val inactiveColor = Color.White.copy(alpha = 0.61f)

                    NavigationBarItem(
                        selected = activeTabIndex == 0,
                        onClick = { viewModel.selectTab(0) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = { Text(getTxt("home", isHiSelected), fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BcmDarkGreen,
                            selectedTextColor = activeColor,
                            indicatorColor = activeColor,
                            unselectedIconColor = inactiveColor,
                            unselectedTextColor = inactiveColor
                        ),
                        modifier = Modifier.testTag("nav_tab_home")
                    )

                    NavigationBarItem(
                        selected = activeTabIndex == 1,
                        onClick = { viewModel.selectTab(1) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.LocationCity,
                                contentDescription = "Pali Unit"
                            )
                        },
                        label = { Text(getTxt("pali", isHiSelected), fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BcmDarkGreen,
                            selectedTextColor = activeColor,
                            indicatorColor = activeColor,
                            unselectedIconColor = inactiveColor,
                            unselectedTextColor = inactiveColor
                        ),
                        modifier = Modifier.testTag("nav_tab_pali")
                    )

                    NavigationBarItem(
                        selected = activeTabIndex == 2,
                        onClick = { viewModel.selectTab(2) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = "Inquiry"
                            )
                        },
                        label = { Text(getTxt("inquire_now", isHiSelected), fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BcmDarkGreen,
                            selectedTextColor = activeColor,
                            indicatorColor = activeColor,
                            unselectedIconColor = inactiveColor,
                            unselectedTextColor = inactiveColor
                        ),
                        modifier = Modifier.testTag("nav_tab_inquire")
                    )

                    NavigationBarItem(
                        selected = activeTabIndex == 3,
                        onClick = { viewModel.selectTab(3) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = "History"
                            )
                        },
                        label = { Text(getTxt("active_inquiries", isHiSelected), fontSize = 9.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BcmDarkGreen,
                            selectedTextColor = activeColor,
                            indicatorColor = activeColor,
                            unselectedIconColor = inactiveColor,
                            unselectedTextColor = inactiveColor
                        ),
                        modifier = Modifier.testTag("nav_tab_history")
                    )
                }
            },
            floatingActionButton = {
                // MULTI-ACTION FLOATING TRIGGER WIDGETS
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    // Call Support Floating button
                    SmallFloatingActionButton(
                        onClick = {
                            launchActionCall(context, "+911800123456")
                        },
                        containerColor = BcmGold,
                        contentColor = BcmDarkGreen,
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Dial toll free customer care",
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // WHATSAPP FLOATING BUTTON WITH RIPPLE RINGS
                    FloatingActionButton(
                        onClick = {
                            launchWhatsAppText(context, getTxt("whats_app_msg", isHiSelected))
                        },
                        containerColor = Color(0xFF25D366),
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .testTag("whats_app_float_btn")
                            .shadow(6.dp, CircleShape)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = "WhatsApp Assistance",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "WhatsApp",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            // Animated Screen Switching for high visual polish
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = {
                        slideInHorizontally(
                            initialOffsetX = { 300 },
                            animationSpec = spring(dampingRatio = 0.85f)
                        ) + fadeIn() togetherWith slideOutHorizontally(
                            targetOffsetX = { -300 },
                            animationSpec = spring(dampingRatio = 0.85f)
                        ) + fadeOut()
                    },
                    label = "ScreenNavigatorFlow"
                ) { screen ->
                    when (screen) {
                        is BcmScreen.Home -> HomeScreenLayout(viewModel, isHiSelected)
                        is BcmScreen.CityProjects -> PaliProjectsLayout(viewModel, isHiSelected, screen.cityName)
                        is BcmScreen.ProjectDetail -> ProjectDetailLayout(viewModel, isHiSelected, screen.project)
                        is BcmScreen.InquiryTab -> InquiryFormLayout(viewModel, isHiSelected)
                    }
                }

                // Show additional History Screen directly when active tab is selected and history is triggered
                if (activeTabIndex == 3) {
                    InquiryHistoryLayout(viewModel, isHiSelected)
                }

                // Custom success dialog
                val showSucc by viewModel.showSuccessDialog.collectAsState()
                if (showSucc) {
                    InquirySuccessDialog(viewModel, isHiSelected)
                }
            }
        }
    }
}

// DROPDOWN SECTION ANIMATION EXPANDABLE IN DRAWERS
@Composable
fun SidebarSection(
    title: String,
    isExpanded: Boolean,
    onHeaderClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onHeaderClick() }
                .padding(vertical = 14.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = BcmGold,
                    letterSpacing = 1.sp
                )
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = "Expand collapsible category dropdown",
                tint = BcmGold
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 8.dp)
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(
                        BorderStroke(0.6.dp, BcmGold.copy(alpha = 0.15f)),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 4.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun DropdownMenuItemText(label: String, onClick: () -> Unit) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = Color.White.copy(alpha = 0.85f),
            fontWeight = FontWeight.Normal
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 16.dp)
    )
}

// ==========================================
// SCREEN 1: HOME PAGE LAYOUT
// ==========================================
@Composable
fun HomeScreenLayout(viewModel: BcmViewModel, isHi: String) {
    val context = LocalContext.current
    val query by viewModel.searchQuery.collectAsState()
    var selectedCategoryFilter by remember { mutableStateOf("all") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen_scroller"),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // TOP RIBBON SEARCH BAR
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(BcmDarkGreen, Color.Transparent)
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    placeholder = {
                        Text(
                            getTxt("search_hint", isHi),
                            color = if (viewModel.isDarkMode.value) TextSecondaryDark else TextSecondaryLight
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search listings",
                            tint = BcmGold
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_bar_input"),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BcmGold,
                        unfocusedBorderColor = BcmGold.copy(alpha = 0.45f),
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    singleLine = true
                )
            }
        }

        // AUTO-SLIDING CAROUSEL IMAGES
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .padding(horizontal = 16.dp)
                    .testTag("slider_carousel"),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BcmGold.copy(alpha = 0.4f)),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                val banners = BcmProjectDataProvider.banners
                var activeIndex by remember { mutableStateOf(0) }

                // Infinite Timer for Auto-Sliding banner
                LaunchedEffect(Unit) {
                    while (true) {
                        delay(4200)
                        activeIndex = (activeIndex + 1) % banners.size
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    // Sliding Crossfade Transition
                    AnimatedContent(
                        targetState = activeIndex,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(800)) togetherWith fadeOut(animationSpec = tween(800))
                        },
                        label = "BannerSlideSwitch"
                    ) { currentSlide ->
                        val banner = banners[currentSlide]
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    viewModel.navigateTo(BcmScreen.CityProjects("Pali"))
                                }
                        ) {
                            BcmAsyncImage(
                                url = banner.imageUrl,
                                contentDescription = banner.titleEn,
                                modifier = Modifier.fillMaxSize()
                            )

                            // Rich Gradient Overlay
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.82f))
                                        )
                                    )
                                )

                            // Overlay Text Info
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = if (isHi == "HI") banner.titleHi else banner.titleEn,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = BcmGold,
                                        fontSize = 18.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = if (isHi == "HI") banner.subtitleHi else banner.subtitleEn,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.85f),
                                        fontSize = 11.sp
                                    ),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    // Indicator Dots on top right
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        banners.forEachIndexed { dotIndex, _ ->
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (activeIndex == dotIndex) BcmGold else Color.White.copy(alpha = 0.5f)
                                    )
                            )
                        }
                    }
                }
            }
        }

        // CATEGORY SECTION (CIRCULAR ICONS)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = getTxt("categories", isHi),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (viewModel.isDarkMode.value) BcmGold else BcmDarkGreen,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Plots Category with click scale animation
                    CategoryCircleItem(
                        title = getTxt("plots", isHi),
                        url = BcmProjectDataProvider.categories[0].imageUrl,
                        isSelected = selectedCategoryFilter == "plots",
                        onClick = {
                            selectedCategoryFilter = if (selectedCategoryFilter == "plots") "all" else "plots"
                        }
                    )

                    // Villas Category
                    CategoryCircleItem(
                        title = getTxt("villas", isHi),
                        url = BcmProjectDataProvider.categories[1].imageUrl,
                        isSelected = selectedCategoryFilter == "villas",
                        onClick = {
                            selectedCategoryFilter = if (selectedCategoryFilter == "villas") "all" else "villas"
                        }
                    )

                    // Flats Category
                    CategoryCircleItem(
                        title = getTxt("flats", isHi),
                        url = BcmProjectDataProvider.categories[2].imageUrl,
                        isSelected = selectedCategoryFilter == "flats",
                        onClick = {
                            selectedCategoryFilter = if (selectedCategoryFilter == "flats") "all" else "flats"
                        }
                    )
                }
            }
        }

        // LOCATION SECTION (CIRCULAR ICONS IN GRID)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = getTxt("locations", isHi),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (viewModel.isDarkMode.value) BcmGold else BcmDarkGreen,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = getTxt("all_locations", isHi),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (viewModel.isDarkMode.value) TextSecondaryDark else TextSecondaryLight
                    ),
                    modifier = Modifier.padding(bottom = 14.dp)
                )

                // Responsive symmetric circular locations grid
                CustomCircularLocationsGrid(
                    locations = BcmProjectDataProvider.locations,
                    onLocationClick = { city ->
                        viewModel.navigateTo(BcmScreen.CityProjects(city))
                    }
                )
            }
        }

        // HOME PAGE LISTINGS STREAM
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = getTxt("projects_list", isHi),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (viewModel.isDarkMode.value) BcmGold else BcmDarkGreen,
                            letterSpacing = 0.5.sp
                        )
                    )

                    // Reset Filters button
                    if (selectedCategoryFilter != "all" || query.isNotEmpty()) {
                        Text(
                            text = getTxt("all", isHi),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = BcmGold,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier
                                .clickable {
                                    selectedCategoryFilter = "all"
                                    viewModel.onSearchQueryChanged("")
                                }
                                .border(1.dp, BcmGold, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                // Filter elements dynamically from state queries
                val allPr = BcmProjectDataProvider.projects
                val searchFiltered = if (query.isEmpty()) {
                    allPr
                } else {
                    allPr.filter {
                        it.name.contains(query, ignoreCase = true) ||
                        it.location.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true)
                    }
                }

                val finalFiltered = if (selectedCategoryFilter == "all") {
                    searchFiltered
                } else {
                    searchFiltered.filter { it.category.lowercase().startsWith(selectedCategoryFilter.lowercase().substring(0, 3)) }
                }

                if (finalFiltered.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "No listings matches filters",
                            tint = BcmGold.copy(alpha = 0.5f),
                            modifier = Modifier.size(42.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No properties matching search filter found.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (viewModel.isDarkMode.value) TextSecondaryDark else TextSecondaryLight
                        )
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        finalFiltered.forEach { project ->
                            HomeProjectRowCard(
                                project = project,
                                isHi = isHi == "HI",
                                onClick = { viewModel.navigateTo(BcmScreen.ProjectDetail(project)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// 1A. CIRCULAR CATEGORY COMPONENT
@Composable
fun CategoryCircleItem(
    title: String,
    url: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scaleFactor by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1.0f,
        animationSpec = spring(dampingRatio = 0.6f)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .graphicsLayer(scaleX = scaleFactor, scaleY = scaleFactor)
            .testTag("category_${title.lowercase()}")
    ) {
        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(
                        width = if (isSelected) 3.5.dp else 1.dp,
                        color = if (isSelected) BcmGold else BcmGold.copy(alpha = 0.35f)
                    ),
                    CircleShape
                )
        ) {
            BcmAsyncImage(
                url = url,
                contentDescription = title,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) BcmGold else MaterialTheme.colorScheme.onSurface,
                fontSize = 11.5.sp
            )
        )
    }
}

// 1B. FLEXIBLE GRID FOR LOCATION LOGOS
@Composable
fun CustomCircularLocationsGrid(
    locations: List<CityLocation>,
    onLocationClick: (String) -> Unit
) {
    Column {
        val chunked = locations.chunked(4)
        chunked.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                rowItems.forEach { city ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { onLocationClick(city.id) }
                            .testTag("location_btn_${city.id.lowercase()}")
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(BcmDarkGreenLight, BcmDarkGreen)
                                    )
                                )
                                .border(1.dp, BcmGold.copy(alpha = 0.5f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                city.iconChar.uppercase(),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = BcmGold,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = city.nameEn,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

// 1C. MODERN CARD FOR PROJECT STREAM
@Composable
fun HomeProjectRowCard(
    project: BcmProject,
    isHi: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(0.6.dp, BcmGold.copy(alpha = 0.25f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circle Project logo thumbnail (Required: Round thumbnail image)
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, BcmGold, CircleShape)
            ) {
                BcmAsyncImage(
                    url = project.imageUrl,
                    contentDescription = project.name,
                    modifier = Modifier.fillMaxSize(),
                    cityInitials = project.name.take(2)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Info column
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = BcmDarkGreenLight
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "city tag",
                        modifier = Modifier.size(14.dp),
                        tint = BcmGold
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${project.location} • ${project.category}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = BcmGoldDark,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isHi) project.priceHi else project.priceEn,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFFA52A2A), // Brick luxury red
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }

            // Arrow action Icon
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Details",
                tint = BcmGold,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// ==========================================
// SCREEN 2: PALI PROJECTS (AND OTHER CITIES) SCREEN
// ==========================================
@Composable
fun PaliProjectsLayout(viewModel: BcmViewModel, isHi: String, cityName: String) {
    val projects = BcmProjectDataProvider.projects.filter { it.location.equals(cityName, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("pali_projects_layout")
    ) {
        // Back icon option
        IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = BcmGold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = getTxt("close", isHi),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = BcmGold,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        Text(
            text = "$cityName Projects",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = BcmDarkGreen,
                letterSpacing = 1.sp
            )
        )

        Text(
            text = "BCM PREMIUM DEVELOPMENT SCHEMES - $cityName UNIT",
            style = MaterialTheme.typography.bodySmall.copy(
                color = BcmGoldDark,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        if (projects.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No current projects launched in $cityName yet. Check back soon!",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Circular project cards as specified in details
            projects.forEach { item ->
                PaliPremiumCircleCard(
                    project = item,
                    isHi = isHi == "HI",
                    onClick = { viewModel.navigateTo(BcmScreen.ProjectDetail(item)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// REQUIRED: Round Project Card including thumbnail image, project name, modern shadow card inside a Pali Screener
@Composable
fun PaliPremiumCircleCard(
    project: BcmProject,
    isHi: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("pali_card_${project.id}")
            .shadow(6.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.2.dp, BcmGold.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // REQUIRED: Circular Project Thumbnail image
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .border(2.5.dp, BcmGold, CircleShape)
                    .shadow(4.dp, CircleShape)
            ) {
                BcmAsyncImage(
                    url = project.imageUrl,
                    contentDescription = project.name,
                    modifier = Modifier.fillMaxSize(),
                    cityInitials = project.name.take(2)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // REQUIRED: Project Name
            Text(
                text = project.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = BcmDarkGreen,
                    letterSpacing = 1.sp
                ),
                textAlign = TextAlign.Center
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = "Verified status tag",
                    tint = BcmGold,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isHi) project.statusHi else project.statusEn,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = BcmGoldDark
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Segment pill decoration
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(BcmDarkGreen)
                    .padding(horizontal = 14.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${project.category} • ${project.size}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = BcmGold,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Divider(color = BcmGold.copy(alpha = 0.15f), thickness = 1.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isHi) project.priceHi else project.priceEn,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFFA52A2A),
                        fontWeight = FontWeight.Black
                    )
                )

                Text(
                    text = getTxt("explore_more", if (isHi) "HI" else "EN") + " →",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = BcmDarkGreen,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }
    }
}

// ==========================================
// SCREEN 3: PROJECT DETAIL SCREEN
// ==========================================
@Composable
fun ProjectDetailLayout(viewModel: BcmViewModel, isHi: String, project: BcmProject) {
    val context = LocalContext.current
    val languageKey = if (isHi == "HI") "HI" else "EN"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .testTag("project_detail_layout")
    ) {
        // High def Header Hero image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {
            BcmAsyncImage(
                url = project.imageUrl,
                contentDescription = project.name,
                modifier = Modifier.fillMaxSize()
            )

            // Overlaid Return Button
            IconButton(
                onClick = { viewModel.navigateBack() },
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(12.dp)
                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = BcmGold
                )
            }

            // Brand floating shield tag
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(BcmDarkGreen)
                    .border(0.6.dp, BcmGold, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = project.category.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = BcmGold,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        // Project Core Details details
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = project.name,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Black,
                            color = BcmDarkGreen,
                            letterSpacing = 0.5.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "Marker",
                            tint = BcmGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${project.location}, Rajasthan",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = BcmGoldDark,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFFFFAEB))
                        .border(1.dp, BcmGold, RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (isHi == "HI") project.priceHi else project.priceEn,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color(0xFFA52A2A),
                            fontWeight = FontWeight.Black
                        )
                    )
                }
            }

            Divider(color = BcmGold.copy(alpha = 0.2f))

            // Highlights Row (Area Size, Permitting, Investment status)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HighlightPill(
                    label = getTxt("size", languageKey),
                    value = project.size,
                    modifier = Modifier.weight(1f)
                )
                HighlightPill(
                    label = getTxt("status", languageKey),
                    value = if (isHi == "HI") project.statusHi else project.statusEn,
                    modifier = Modifier.weight(1f)
                )
            }

            // Description block
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.05f))
                    .border(0.5.dp, BcmGold.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Description / विवरण",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = BcmDarkGreen
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isHi == "HI") project.descriptionHi else project.descriptionEn,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 22.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                )
            }

            // INQUIRE NOW ACTIONS BUTTON PRE-FILLS INQUIRY FORM DIRECTLY!
            Button(
                onClick = {
                    viewModel.prefillInquiry(project.name, project.category)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("inquire_prefill_btn"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BcmDarkGreen,
                    contentColor = BcmGold
                ),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.2.dp, BcmGold)
            ) {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = null,
                    tint = BcmGold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = getTxt("inquire_now", languageKey).uppercase(),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            // Contact Direct support line details
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = BcmDarkGreen.copy(alpha = 0.05f)
                ),
                border = BorderStroke(0.5.dp, BcmGold)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = { launchActionCall(context, "+911800123456") }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = "Call", tint = BcmDarkGreen)
                            Text("Call Support", fontSize = 10.sp, color = BcmDarkGreen, fontWeight = FontWeight.Bold)
                        }
                    }

                    IconButton(onClick = { launchWhatsAppText(context, getTxt("whats_app_msg", languageKey)) }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.Default.Message, contentDescription = "WhatsApp", tint = Color(0xFF25D366))
                            Text("WhatsApp", fontSize = 10.sp, color = Color(0xFF25D366), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HighlightPill(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .border(0.5.dp, BcmGold.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = BcmGoldDark,
                    fontSize = 9.sp
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = BcmDarkGreen,
                    fontSize = 11.sp
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// ==========================================
// SCREEN 4: PROPERTY INQUIRY FORM
// ==========================================
@Composable
fun InquiryFormLayout(viewModel: BcmViewModel, isHi: String) {
    val context = LocalContext.current
    val languageKey = if (isHi == "HI") "HI" else "EN"

    // Bind fields to flow state
    val formName by viewModel.formName.collectAsState()
    val formPhone by viewModel.formPhone.collectAsState()
    val formEmail by viewModel.formEmail.collectAsState()
    val prType by viewModel.formPropertyType.collectAsState()
    val selectedProj by viewModel.formSelectedProject.collectAsState()
    val budget by viewModel.formBudget.collectAsState()
    val customMsg by viewModel.formMessage.collectAsState()

    var nameError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    val propertyTypologyOptions = listOf("Plots", "Villas", "Flats")
    val budgetScales = listOf("₹10 Lakhs - ₹25 Lakhs", "₹25 Lakhs - ₹50 Lakhs", "₹50 Lakhs - ₹1 Crore", "₹1 Crore+")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("inquiry_form_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = getTxt("submit", languageKey),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Black,
                color = BcmDarkGreen,
                letterSpacing = 1.sp
            )
        )

        Text(
            text = "BCM GRP EXCLUSIVE PROPERTY INVESTMENT INQUIRY PORTAL",
            style = MaterialTheme.typography.bodySmall.copy(
                color = BcmGoldDark,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // CARD WRAP
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BcmGold.copy(alpha = 0.35f)),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Client Name
                Column {
                    OutlinedTextField(
                        value = formName,
                        onValueChange = {
                            viewModel.updateFormName(it)
                            if (it.isNotEmpty()) nameError = false
                        },
                        label = { Text(getTxt("name", languageKey)) },
                        isError = nameError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("form_name_field"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BcmGold,
                            unfocusedBorderColor = BcmGold.copy(alpha = 0.4f),
                            focusedLabelColor = BcmDarkGreen
                        )
                    )
                    if (nameError) {
                        Text("Name is highly required *", color = MaterialTheme.colorScheme.error, fontSize = 10.sp, modifier = Modifier.padding(start = 6.dp))
                    }
                }

                // Phone/WhatsApp number
                Column {
                    OutlinedTextField(
                        value = formPhone,
                        onValueChange = {
                            viewModel.updateFormPhone(it)
                            if (it.isNotEmpty()) phoneError = false
                        },
                        label = { Text(getTxt("phone", languageKey)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        isError = phoneError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("form_phone_field"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BcmGold,
                            unfocusedBorderColor = BcmGold.copy(alpha = 0.4f),
                            focusedLabelColor = BcmDarkGreen
                        )
                    )
                    if (phoneError) {
                        Text("Active contact number is required *", color = MaterialTheme.colorScheme.error, fontSize = 10.sp, modifier = Modifier.padding(start = 6.dp))
                    }
                }

                // Email box
                OutlinedTextField(
                    value = formEmail,
                    onValueChange = { viewModel.updateFormEmail(it) },
                    label = { Text(getTxt("email", languageKey)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BcmGold,
                        unfocusedBorderColor = BcmGold.copy(alpha = 0.4f)
                    )
                )

                // Typology dropdown toggle custom
                Text("Select Segment / श्रेणी :", fontWeight = FontWeight.Bold, color = BcmDarkGreen, fontSize = 13.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    propertyTypologyOptions.forEach { typeOption ->
                        val isSel = prType == typeOption
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) BcmDarkGreen else Color.White.copy(alpha = 0.05f))
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (isSel) BcmGold else BcmGold.copy(alpha = 0.3f)
                                    ),
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { viewModel.updateFormPropertyType(typeOption) }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = typeOption,
                                fontWeight = FontWeight.Bold,
                                color = if (isSel) BcmGold else MaterialTheme.colorScheme.onSurface,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                // Project Selector Title
                Column {
                    Text("Associated Project / प्रोजेक्ट का चयन :", fontWeight = FontWeight.Bold, color = BcmDarkGreen, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(BcmDarkGreen)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = selectedProj,
                            color = BcmGold,
                            fontWeight = FontWeight.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    // Project selector quick buttons
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(BcmProjectDataProvider.projects.map { it.name }.distinct()) { itemProjName ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color.White)
                                    .border(0.6.dp, BcmGold.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                                    .clickable { viewModel.updateFormSelectedProject(itemProjName) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(itemProjName, color = BcmDarkGreen, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }

                // Budget Selector custom choice
                Column {
                    Text("Budget Level / संपति मूल्य चयन :", fontWeight = FontWeight.Bold, color = BcmDarkGreen, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        budgetScales.forEach { budgetScale ->
                            val isChosen = budget == budgetScale
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isChosen) BcmGold else Color.Transparent)
                                    .border(1.dp, BcmGold, RoundedCornerShape(20.dp))
                                    .clickable { viewModel.updateFormBudget(budgetScale) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    budgetScale,
                                    color = if (isChosen) BcmDarkGreen else MaterialTheme.colorScheme.onSurface,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Requirements Custom note
                OutlinedTextField(
                    value = customMsg,
                    onValueChange = { viewModel.updateFormMessage(it) },
                    label = { Text(getTxt("message", languageKey)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BcmGold,
                        unfocusedBorderColor = BcmGold.copy(alpha = 0.4f)
                    ),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(10.dp))

                // SUBMIT TRIGGER
                Button(
                    onClick = {
                        if (formName.trim().isEmpty()) {
                            nameError = true
                        }
                        if (formPhone.trim().isEmpty()) {
                            phoneError = true
                        }

                        if (!nameError && !phoneError) {
                            viewModel.submitInquiry { ok ->
                                if (ok) {
                                    Toast.makeText(context, "Inquiry Lodged Locally!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Please fulfill required forms.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("form_submit_btn"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BcmDarkGreen,
                        contentColor = BcmGold
                    ),
                    shape = RoundedCornerShape(26.dp),
                    border = BorderStroke(1.2.dp, BcmGold)
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null, tint = BcmGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = getTxt("submit", languageKey).uppercase(),
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

// ==========================================
// SCREEN 5: INQUIRIES SUBMISSION HISTORY
// ==========================================
@Composable
fun InquiryHistoryLayout(viewModel: BcmViewModel, isHi: String) {
    val itemsFlow by viewModel.inquiriesFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .testTag("history_screen_root")
    ) {
        Text(
            text = getTxt("active_inquiries", isHi),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                color = BcmDarkGreen,
                letterSpacing = 1.sp
            )
        )
        Text(
            text = "PREVIOUS CONTACT LOGS WITH AGENTS (SQLITE PERSISTED)",
            style = MaterialTheme.typography.labelSmall.copy(
                color = BcmGoldDark,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (itemsFlow.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Inventory,
                        contentDescription = "Empty",
                        modifier = Modifier.size(54.dp),
                        tint = BcmGold.copy(alpha = 0.35f)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = getTxt("no_inquiries", isHi),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            itemsFlow.forEach { log ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(0.6.dp, BcmGold.copy(alpha = 0.4f)),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = log.clientName,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = BcmDarkGreenLight
                                )
                            )

                            // Quick delete entry
                            IconButton(
                                onClick = { viewModel.deleteInquiry(log.id) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove entry",
                                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Divider(color = BcmGold.copy(alpha = 0.12f))

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            InfoRow(tag = "Phone:", value = log.phoneNumber)
                            InfoRow(tag = "Project:", value = log.selectedProject)
                            InfoRow(tag = "Details:", value = "${log.propertyType} • ${log.clientBudget}")
                            InfoRow(tag = "Msg:", value = log.message)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(tag: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$tag ",
            fontWeight = FontWeight.SemiBold,
            color = BcmGoldDark,
            fontSize = 11.5.sp,
            modifier = Modifier.width(60.dp)
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 11.5.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// ==========================================
// CUSTOM SUCCESS DIALOG WITH BRAND GRAPHIC
// ==========================================
@Composable
fun InquirySuccessDialog(viewModel: BcmViewModel, isHi: String) {
    Dialog(onDismissRequest = { viewModel.dismissSuccessDialog() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(12.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BcmDarkGreen),
            border = BorderStroke(2.dp, BcmGold)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Success Crest circular logo
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(BcmGold),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Confirmed",
                        tint = BcmDarkGreen,
                        modifier = Modifier.size(46.dp)
                    )
                }

                Text(
                    text = getTxt("success", isHi),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = BcmGold,
                        letterSpacing = 1.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = getTxt("success_msg", isHi),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 20.sp
                    ),
                    textAlign = TextAlign.Center
                )

                // Close dialog action button
                Button(
                    onClick = { viewModel.dismissSuccessDialog() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BcmGold,
                        contentColor = BcmDarkGreen
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().testTag("dialog_dismiss_btn")
                ) {
                    Text(
                        getTxt("close", isHi).uppercase(),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// REAL INTENTS SYSTEM TRIGGERS FOR NO-MOCK MANDATE
fun launchActionCall(context: Context, phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Dialer action is unavailable on this device model.", Toast.LENGTH_SHORT).show()
    }
}

fun launchWhatsAppText(context: Context, text: String) {
    try {
        val formattedMsg = URLEncoder.encode(text, "UTF-8")
        val intent = Intent(Intent.ACTION_VIEW).apply {
            // Real target numbers for agents
            data = Uri.parse("https://api.whatsapp.com/send?phone=+918107567848&text=$formattedMsg")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        // Fallback standard text shares
        val textIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(textIntent, "Contact BCM sales via:"))
    }
}
