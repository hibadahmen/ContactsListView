package com.example.contactslistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ContactAdapter(
    context: Context,
    contacts: MutableList<String>
) : ArrayAdapter<String>(context, R.layout.item_contact, contacts) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        // Réutiliser une ligne existante ou en créer une
        val ligne = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_contact,
            parent,
            false
        )

        // Récupérer les éléments de la ligne
        val tvInitiale = ligne.findViewById<TextView>(R.id.tvInitiale)
        val tvNom = ligne.findViewById<TextView>(R.id.tvNom)

        // Récupérer le nom à cette position
        val nom = getItem(position).orEmpty()

        // Afficher le nom et sa première lettre
        tvNom.text = nom
        tvInitiale.text = nom.trim().take(1).uppercase()

        return ligne
    }
}