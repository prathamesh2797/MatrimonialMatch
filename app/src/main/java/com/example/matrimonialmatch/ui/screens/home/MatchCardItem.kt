package com.example.matrimonialmatch.ui.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.matrimonialmatch.domain.model.UserProfile
import com.example.matrimonialmatch.ui.theme.MatrimonialMatchTheme

@Composable
fun MatchCard(
    profile: UserProfile,
    onAccept: (UserProfile) -> Unit,
    onDecline: (UserProfile) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Image
            AsyncImage(
                model = profile.imageUrl,
                contentDescription = profile.name,
                modifier = Modifier.size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Name
            Text(
                text = profile.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF1E88E5), // Blue-ish
                    fontWeight = FontWeight.Bold
                )
            )

            // Age + Location
            Text(
                text = "${profile.age}, ${profile.city}\n${profile.state}, ${profile.country}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Accept / Decline Buttons
            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { onDecline(profile) }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Decline",
                        tint = Color(0xFF1E88E5),
                        modifier = Modifier
                            .size(40.dp)
                            .border(2.dp, Color(0xFF1E88E5), CircleShape)
                            .padding(8.dp)
                    )
                }
                IconButton(onClick = { onAccept(profile) }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Accept",
                        tint = Color(0xFF1E88E5),
                        modifier = Modifier
                            .size(40.dp)
                            .border(2.dp, Color(0xFF1E88E5), CircleShape)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun MatchCardPreview() {
    MatrimonialMatchTheme {
        val sampleProfile = UserProfile(
            id = "1",
            name = "Adilson Pultrum",
            age = 56,
            city = "Oudega gem",
            state = "SmallingerInd",
            country = "Drenthe",
            imageUrl = "https://randomuser.me/api/portraits/thumb/men/98.jpg"
        )
        MatchCard(
            profile = sampleProfile,
            onAccept = {},
            onDecline = {}
        )
    }
}*/
