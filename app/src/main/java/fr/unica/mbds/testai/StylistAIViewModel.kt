package fr.unica.mbds.testai

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StylistAIViewModel : ViewModel() {

    // État de l'interface utilisateur, observé par les composables
    var weatherInfo by mutableStateOf("Loading weather...")
        private set

    var currentMood by mutableStateOf("")
        private set

    var selectedStyle by mutableStateOf("Casual")
        private set

    var outfitSuggestion by mutableStateOf("")
        private set

    var isGenerating by mutableStateOf(false)
        private set

    // Fonctions pour modifier l'état en réponse aux événements utilisateur ou aux données
    fun updateMood(newMood: String) {
        currentMood = newMood
    }

    fun updateStyle(newStyle: String) {
        selectedStyle = newStyle
    }

    fun fetchWeather() {
        // TODO: Implement fetching weather data from an API
        // For now, simulate a delay and update
        viewModelScope.launch {
            isGenerating = true // Simulate loading
            kotlinx.coroutines.delay(2000) // Simulate network delay
            weatherInfo = "Sunny, 28°C" // Replace with actual weather data
            isGenerating = false
        }
    }

    fun generateOutfitSuggestion() {
        viewModelScope.launch {
            isGenerating = true
            outfitSuggestion = "Generating outfit..." // Provide immediate feedback

            // TODO: Call your chosen AI API here (Gemini, etc.)
            // Use currentMood, selectedStyle, and weatherInfo to form the prompt
            val prompt = "Suggest an outfit for someone who feels $currentMood, prefers a $selectedStyle style, and the weather is $weatherInfo."

            // Simulate an API call delay
            kotlinx.coroutines.delay(3000)

            // Replace with the actual response from the AI API
            outfitSuggestion = "Based on your mood '$currentMood' and '$selectedStyle' style in '$weatherInfo' weather, I suggest: A comfortable t-shirt, dark wash jeans, and walking shoes. Bring a light jacket just in case."

            isGenerating = false
        }
    }

    // Initialisation : charger la météo au démarrage
    init {
        fetchWeather()
    }
}