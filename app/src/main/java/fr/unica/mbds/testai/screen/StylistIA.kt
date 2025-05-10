package fr.unica.mbds.testai.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face // Placeholder
import androidx.compose.material.icons.filled.WbSunny // Exemple pour ensoleillé
import androidx.compose.material.icons.filled.Cloud // Exemple pour nuageux
import androidx.compose.material.icons.filled.Grain // Exemple pour pluvieux/neigeux
import androidx.compose.material.icons.filled.FlashOn // Exemple pour orageux
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unica.mbds.testai.ui.theme.TestAITheme // Assurez-vous d'avoir votre thème

// Composable principal de l'écran StylistAI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StylistAIScreen(
    weatherInfo: String,
    currentMood: String,
    onMoodChange: (String) -> Unit,
    selectedStyle: String,
    onStyleChange: (String) -> Unit,
    outfitSuggestion: String,
    onGenerateOutfitClick: () -> Unit,
    isGenerating: Boolean // Pour indiquer si l'IA travaille
) {
    // Déterminer la couleur de fond en fonction de la météo
    val backgroundColor = getWeatherBackgroundColor(weatherInfo)
    // Animer la transition de couleur pour une meilleure esthétique
    val animatedBackgroundColor by animateColorAsState(
        targetValue = backgroundColor,
        animationSpec = tween(durationMillis = 500) // Animation de 500ms
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("StylistAI") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .background(animatedBackgroundColor), // Appliquer la couleur de fond animée
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            WeatherDisplay(weatherInfo = weatherInfo)

            MoodInput(currentMood = currentMood, onMoodChange = onMoodChange)

            StyleSelector(selectedStyle = selectedStyle, onStyleChange = onStyleChange)

            Button(
                onClick = onGenerateOutfitClick,
                enabled = !isGenerating // Désactiver le bouton pendant la génération
            ) {
                if (isGenerating) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                    Spacer(Modifier.width(8.dp))
                    Text("Generating...")
                } else {
                    Text("Suggest Outfit")
                }
            }

            OutfitSuggestionDisplay(suggestion = outfitSuggestion)

            // Bonus: Placeholder pour l'image générée
            // Si vous implémentez la génération d'images, vous placerez
            // ici un composable pour afficher l'image.
            // Par exemple: ImageDisplay(imageUrl = imageUrl)
        }
    }
}

// Fonction pour déterminer la couleur de fond dans un style plus élégant
// Utilise la couleur par défaut pour le temps ensoleillé.
@Composable
fun getWeatherBackgroundColor(weatherInfo: String): Color {
    return when {
        weatherInfo.contains("Sunny", ignoreCase = true) -> MaterialTheme.colorScheme.background // Utilise la couleur de fond du thème
        weatherInfo.contains("Cloudy", ignoreCase = true) -> Color(0xFFC0C0C0) // Gris clair
        weatherInfo.contains("Rain", ignoreCase = true) || weatherInfo.contains("Showers", ignoreCase = true) -> Color(0xFFA0B0C0) // Un bleu-gris doux
        weatherInfo.contains("Snow", ignoreCase = true) -> Color(0xFFF5F5F5) // Blanc cassé
        weatherInfo.contains("Thunderstorm", ignoreCase = true) -> Color(0xFF808080) // Gris moyen
        else -> MaterialTheme.colorScheme.background // Couleur de fond par défaut du thème
    }
}

// Composable pour afficher les informations météorologiques
@Composable
fun WeatherDisplay(weatherInfo: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val weatherIcon = when {
            weatherInfo.contains("Sunny", ignoreCase = true) -> Icons.Default.WbSunny
            weatherInfo.contains("Cloudy", ignoreCase = true) -> Icons.Default.Cloud
            weatherInfo.contains("Rain", ignoreCase = true) || weatherInfo.contains("Showers", ignoreCase = true) -> Icons.Default.Grain // Icône pour la pluie/neige
            weatherInfo.contains("Snow", ignoreCase = true) -> Icons.Default.Grain
            weatherInfo.contains("Thunderstorm", ignoreCase = true) -> Icons.Default.FlashOn
            else -> Icons.Default.Face // Icône par défaut
        }
        Icon(
            imageVector = weatherIcon,
            contentDescription = "Weather Icon",
            modifier = Modifier.size(40.dp),
            tint = MaterialTheme.colorScheme.onBackground // Utiliser une couleur du thème pour l'icône
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Current Weather: $weatherInfo",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground // Utiliser une couleur du thème pour le texte
        )
    }
}

// Composable pour la saisie de l'humeur

// Composable pour la saisie de l'humeur
@OptIn(ExperimentalMaterial3Api::class) // Nécessaire si l'API colors est expérimentale
@Composable
fun MoodInput(currentMood: String, onMoodChange: (String) -> Unit) {
    TextField(
        value = currentMood,
        onValueChange = onMoodChange,
        label = { Text("How do you feel today?") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        // Utiliser TextFieldDefaults.colors pour Material Design 3
        colors = TextFieldDefaults.colors(
            // Appliquer la couleur de fond aux états focalisé et non focalisé
            focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
            unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
            // Vous pouvez laisser les autres couleurs par défaut ou les personnaliser ici
            // Par exemple:
            // focusedIndicatorColor = Color.Transparent,
            // unfocusedIndicatorColor = Color.Transparent,
            // disabledIndicatorColor = Color.Transparent,
            // errorIndicatorColor = MaterialTheme.colorScheme.error,
            // cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

// Composable pour sélectionner le style préféré
@Composable
fun StyleSelector(selectedStyle: String, onStyleChange: (String) -> Unit) {
    val styles = listOf("Classic", "Casual", "Sporty", "Chic", "Elegant")
}
    // Composable pour afficher la suggestion de tenue de l'IA
    @Composable
    fun OutfitSuggestionDisplay(suggestion: String) {
        Card(
            modifier = Modifier
                .fillMaxWidth(), // La carte prend toute la largeur disponible
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Ajoute une ombre légère
            shape = MaterialTheme.shapes.medium // Utilise une forme moyenne du thème (coins arrondis par défaut)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp) // Ajoute un padding à l'intérieur de la carte
            ) {
                Text(
                    text = "Suggested Outfit:",
                    style = MaterialTheme.typography.titleMedium, // Utilise un style de titre du thème
                    color = MaterialTheme.colorScheme.primary // Utilise la couleur principale du thème
                )
                Spacer(modifier = Modifier.height(8.dp)) // Ajoute un espace vertical
                Text(
                    text = suggestion,
                    style = MaterialTheme.typography.bodyMedium, // Utilise un style de corps de texte du thème
                    color = MaterialTheme.colorScheme.onSurfaceVariant // Couleur secondaire pour le texte
                )
            }

    }
}