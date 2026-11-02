package com.example.contactslistview


import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val contacts = mutableListOf(
        "Ahmed Ben Ali",
        "Sarra Trabelsi",
        "Mohamed Gharbi",
        "Ines Jaziri",
        "Youssef Mansouri",
        "Amira Chaabane",
        "Karim Bouazizi",
        "Nour Hammami"
    )

    private lateinit var adapter: ContactAdapter

    private lateinit var etContact: EditText
    private lateinit var btnModifier: Button
    private lateinit var lvContacts: ListView

    private var positionSelectionnee = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Éviter le chevauchement avec les barres système.
        val main = findViewById<View>(R.id.main)

        val paddingLeft = main.paddingLeft
        val paddingTop = main.paddingTop
        val paddingRight = main.paddingRight
        val paddingBottom = main.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(main) { view, insets ->
            val barres = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                paddingLeft + barres.left,
                paddingTop + barres.top,
                paddingRight + barres.right,
                paddingBottom + barres.bottom
            )

            insets
        }

        ViewCompat.requestApplyInsets(main)

        // Récupération des éléments de l'interface.
        etContact = findViewById(R.id.etContact)
        btnModifier = findViewById(R.id.btnModifier)
        lvContacts = findViewById(R.id.lvContacts)

        // Adaptateur personnalisé : initiale + nom du contact.
        adapter = ContactAdapter(this, contacts)
        lvContacts.adapter = adapter

        // Sélection d'un contact.
        lvContacts.setOnItemClickListener { _, _, position, _ ->
            positionSelectionnee = position

            lvContacts.setItemChecked(position, true)

            etContact.error = null
            etContact.setText(contacts[position])
            etContact.setSelection(etContact.text.length)
            etContact.requestFocus()
        }

        // Enregistrement avec le bouton.
        btnModifier.setOnClickListener {
            modifierContact()
        }

        // Enregistrement avec la touche "Terminé" du clavier.
        etContact.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                modifierContact()
                true
            } else {
                false
            }
        }
    }

    private fun modifierContact() {

        // Vérifier la sélection.
        if (positionSelectionnee == -1) {
            Toast.makeText(
                this,
                R.string.msg_aucune_selection,
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // Lire le nouveau nom.
        val nouveauNom = etContact.text.toString().trim()

        // Refuser un nom vide ou composé uniquement d'espaces.
        if (nouveauNom.isEmpty()) {
            etContact.error = getString(R.string.msg_nom_vide)
            etContact.requestFocus()
            return
        }

        // Modifier la liste.
        contacts[positionSelectionnee] = nouveauNom

        // Actualiser les noms et les initiales affichés.
        adapter.notifyDataSetChanged()

        etContact.error = null

        Toast.makeText(
            this,
            getString(R.string.msg_contact_modifie, nouveauNom),
            Toast.LENGTH_SHORT
        ).show()
    }
}