package br.zampnrs.viacepapi_example.presentation.contact.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.zampnrs.viacepapi_example.databinding.ActivityContactBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}