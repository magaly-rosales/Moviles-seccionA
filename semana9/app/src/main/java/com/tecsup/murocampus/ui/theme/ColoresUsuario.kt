package com.tecsup.murocampus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun colorUsuario(userId: Int): Pair<Color, Color> {
    val isDark = isSystemInDarkTheme()
    return when (userId % 10) {
        1 -> if (isDark) Pair(Color(0xFF3B2D54), Color(0xFFE8DDFF)) else Pair(Color(0xFFF3EDF7), Color(0xFF6750A4))
        2 -> if (isDark) Pair(Color(0xFF1E3A3A), Color(0xFFB2ECE1)) else Pair(Color(0xFFE0F2F1), Color(0xFF00695C))
        3 -> if (isDark) Pair(Color(0xFF4A2800), Color(0xFFFFDCC3)) else Pair(Color(0xFFFFF3E0), Color(0xFFE65100))
        4 -> if (isDark) Pair(Color(0xFF1B3854), Color(0xFFCBE6FF)) else Pair(Color(0xFFE1F5FE), Color(0xFF0277BD))
        5 -> if (isDark) Pair(Color(0xFF421D30), Color(0xFFFFD8EC)) else Pair(Color(0xFFFCE4EC), Color(0xFFC2185B))
        6 -> if (isDark) Pair(Color(0xFF2A3B28), Color(0xFFC8ECE1)) else Pair(Color(0xFFE8F5E9), Color(0xFF2E7D32))
        7 -> if (isDark) Pair(Color(0xFF4A3800), Color(0xFFFFE0B2)) else Pair(Color(0xFFFFF8E1), Color(0xFFF57F17))
        8 -> if (isDark) Pair(Color(0xFF3E2723), Color(0xFFD7CCC8)) else Pair(Color(0xFFEFEBE9), Color(0xFF4E342E))
        9 -> if (isDark) Pair(Color(0xFF263238), Color(0xFFCFD8DC)) else Pair(Color(0xFFECEFF1), Color(0xFF37474F))
        0 -> if (isDark) Pair(Color(0xFF4A148C), Color(0xFFEA80FC)) else Pair(Color(0xFFF3E5F5), Color(0xFF7B1FA2))
        else -> if (isDark) Pair(Color(0xFF3B2D54), Color(0xFFE8DDFF)) else Pair(Color(0xFFF3EDF7), Color(0xFF6750A4))
    }
}
