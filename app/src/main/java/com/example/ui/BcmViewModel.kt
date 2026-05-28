package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.BcmProject
import com.example.data.BcmProjectDataProvider
import com.example.data.InquiryEntity
import com.example.data.InquiryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Screen Stack Navigation definition
sealed interface BcmScreen {
    object Home : BcmScreen
    data class CityProjects(val cityName: String) : BcmScreen
    data class ProjectDetail(val project: BcmProject) : BcmScreen
    object InquiryTab : BcmScreen
}

class BcmViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = InquiryRepository(db.inquiryDao())

    // All inquiries flow from database
    val inquiriesFlow: StateFlow<List<InquiryEntity>> = repository.allInquiries
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Language State: "EN" (English) or "HI" (Hindi)
    private val _language = MutableStateFlow("EN")
    val language: StateFlow<String> = _language.asStateFlow()

    // Dark Mode Override: default dynamic, but toggle-able UI
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    // Navigation Stack (List of screens, screen at the end is active)
    private val _navigationStack = MutableStateFlow<List<BcmScreen>>(listOf(BcmScreen.Home))
    val navigationStack: StateFlow<List<BcmScreen>> = _navigationStack.asStateFlow()

    // Bottom Tab Bar Active state: 0 = Home, 1 = Pali Projects (or general search), 2 = Inquiry Form, 3 = History
    private val _activeTab = MutableStateFlow(0)
    val activeTab: StateFlow<Int> = _activeTab.asStateFlow()

    // Sidebar drawer expansion states
    private val _isPlotsDropdownExpanded = MutableStateFlow(false)
    val isPlotsDropdownExpanded: StateFlow<Boolean> = _isPlotsDropdownExpanded.asStateFlow()

    private val _isVillasDropdownExpanded = MutableStateFlow(false)
    val isVillasDropdownExpanded: StateFlow<Boolean> = _isVillasDropdownExpanded.asStateFlow()

    private val _isFlatsDropdownExpanded = MutableStateFlow(false)
    val isFlatsDropdownExpanded: StateFlow<Boolean> = _isFlatsDropdownExpanded.asStateFlow()

    // Search query state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Inquiry Input Form states
    private val _formName = MutableStateFlow("")
    val formName: StateFlow<String> = _formName.asStateFlow()

    private val _formPhone = MutableStateFlow("")
    val formPhone: StateFlow<String> = _formPhone.asStateFlow()

    private val _formEmail = MutableStateFlow("")
    val formEmail: StateFlow<String> = _formEmail.asStateFlow()

    private val _formPropertyType = MutableStateFlow("Plots") // default
    val formPropertyType: StateFlow<String> = _formPropertyType.asStateFlow()

    private val _formSelectedProject = MutableStateFlow("BCM Vihar Pali")
    val formSelectedProject: StateFlow<String> = _formSelectedProject.asStateFlow()

    private val _formBudget = MutableStateFlow("₹15 Lakhs - ₹30 Lakhs")
    val formBudget: StateFlow<String> = _formBudget.asStateFlow()

    private val _formMessage = MutableStateFlow("")
    val formMessage: StateFlow<String> = _formMessage.asStateFlow()

    // Notification / Dialog states
    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog: StateFlow<Boolean> = _showSuccessDialog.asStateFlow()

    // Filtering logic based on search queries
    val filteredProjects: StateFlow<List<BcmProject>> = MutableStateFlow(BcmProjectDataProvider.projects)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BcmProjectDataProvider.projects
        )

    fun setLanguage(lang: String) {
        _language.value = lang
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun togglePlotsDropdown() {
        _isPlotsDropdownExpanded.value = !_isPlotsDropdownExpanded.value
    }

    fun toggleVillasDropdown() {
        _isVillasDropdownExpanded.value = !_isVillasDropdownExpanded.value
    }

    fun toggleFlatsDropdown() {
        _isFlatsDropdownExpanded.value = !_isFlatsDropdownExpanded.value
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    // Stack based navigation controls
    fun navigateTo(screen: BcmScreen) {
        val current = _navigationStack.value.toMutableList()
        // If navigating to home, reset stack
        if (screen is BcmScreen.Home) {
            _navigationStack.value = listOf(BcmScreen.Home)
            _activeTab.value = 0
            return
        }
        // Avoid duplicate screens adjacent
        if (current.lastOrNull() != screen) {
            current.add(screen)
            _navigationStack.value = current
        }
        // Sync active tabs for primary entries
        when (screen) {
            is BcmScreen.Home -> _activeTab.value = 0
            is BcmScreen.InquiryTab -> _activeTab.value = 2
            is BcmScreen.CityProjects -> {
                if (screen.cityName == "Pali") {
                    _activeTab.value = 1
                }
            }
            else -> {}
        }
    }

    fun navigateBack(): Boolean {
        val current = _navigationStack.value.toMutableList()
        if (current.size > 1) {
            current.removeAt(current.size - 1)
            _navigationStack.value = current
            // Sync tab
            val nextActive = current.lastOrNull()
            when (nextActive) {
                is BcmScreen.Home -> _activeTab.value = 0
                is BcmScreen.InquiryTab -> _activeTab.value = 2
                is BcmScreen.CityProjects -> {
                    if (nextActive.cityName == "Pali") {
                        _activeTab.value = 1
                    }
                }
                else -> {}
            }
            return true // handled
        }
        return false // let system handle
    }

    fun selectTab(index: Int) {
        _activeTab.value = index
        when (index) {
            0 -> navigateTo(BcmScreen.Home)
            1 -> navigateTo(BcmScreen.CityProjects("Pali")) // Requirement: Pali Projects detail quick-view tab
            2 -> navigateTo(BcmScreen.InquiryTab)
            3 -> {
                // Inquiry History
                _activeTab.value = 3
            }
        }
    }

    // Form inputs mutations
    fun updateFormName(value: String) { _formName.value = value }
    fun updateFormPhone(value: String) { _formPhone.value = value }
    fun updateFormEmail(value: String) { _formEmail.value = value }
    fun updateFormPropertyType(value: String) { _formPropertyType.value = value }
    fun updateFormSelectedProject(value: String) { _formSelectedProject.value = value }
    fun updateFormBudget(value: String) { _formBudget.value = value }
    fun updateFormMessage(value: String) { _formMessage.value = value }

    fun prefillInquiry(projectName: String, category: String) {
        _formSelectedProject.value = projectName
        _formPropertyType.value = category
        navigateTo(BcmScreen.InquiryTab)
    }

    fun dismissSuccessDialog() {
        _showSuccessDialog.value = false
    }

    fun submitInquiry(onResult: (Boolean) -> Unit) {
        val name = _formName.value.trim()
        val phone = _formPhone.value.trim()
        val email = _formEmail.value.trim()
        val project = _formSelectedProject.value
        val prType = _formPropertyType.value
        val budget = _formBudget.value
        val msg = _formMessage.value

        if (name.isEmpty() || phone.isEmpty()) {
            onResult(false)
            return
        }

        viewModelScope.launch {
            val entity = InquiryEntity(
                clientName = name,
                phoneNumber = phone,
                emailAddress = email.ifEmpty { "N/A" },
                selectedProject = project,
                propertyType = prType,
                clientBudget = budget,
                message = msg.ifEmpty { "Interested in details" }
            )
            repository.insertInquiry(entity)
            _showSuccessDialog.value = true
            // Reset input boxes
            _formName.value = ""
            _formPhone.value = ""
            _formEmail.value = ""
            _formMessage.value = ""
            onResult(true)
        }
    }

    fun deleteInquiry(id: Int) {
        viewModelScope.launch {
            repository.deleteInquiry(id)
        }
    }
}
