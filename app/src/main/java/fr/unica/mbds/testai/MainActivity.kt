package fr.unica.mbds.testai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge // Optionnel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.unica.mbds.testai.ui.components.StylistAIScreen // Assurez-vous du chemin d'importation correct
import fr.unica.mbds.testai.ui.theme.TestAITheme // Assurez-vous d'utiliser votre thème

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Optionnel, pour afficher le contenu derrière les barres système
        setContent {
            TestAITheme { // Assurez-vous d'utiliser votre thème Compose
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // La couleur de fond de la Surface peut rester celle du thème,
                    // car le composable StylistAIScreen gère son propre fond animé.
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Obtenez une instance du ViewModel
                    val viewModel: StylistAIViewModel = viewModel()

                    // Observez l'état du ViewModel et passez-le au composable
                    // Nous utilisons `by remember { mutableStateOf(...) }` dans le ViewModel
                    // donc l'observation est automatique avec l'utilisation directe des propriétés.
                    // Si vous utilisiez StateFlow/MutableStateFlow, vous utiliseriez collectAsState()

                    StylistAIScreen(
                        weatherInfo = viewModel.weatherInfo,
                        currentMood = viewModel.currentMood,
                        onMoodChange = viewModel::updateMood,
                        selectedStyle = viewModel.selectedStyle,
                        onStyleChange = viewModel::updateStyle,
                        outfitSuggestion = viewModel.outfitSuggestion,
                        onGenerateOutfitClick = viewModel::generateOutfitSuggestion,
                        isGenerating = viewModel.isGenerating
                    )
                }
            }
        }
    }
}