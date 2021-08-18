package com.norm.news.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.navArgs
import coil.load
import com.norm.news.R
import com.norm.news.databinding.ActivityNewsDetailsBinding
import com.norm.news.util.toFormattedDateString

class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailsBinding

    private val args by navArgs<NewsDetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.newsDetailImageView.load(args.article.urlToImage) {
            crossfade(600)
            error(R.drawable.error_placeholder_image)
        }

        binding.newsDetailPublishAtTextView.text =
            args.article.publishedAt.toFormattedDateString("MMM d, yyyy h:mm a")
        binding.newsDetailSourceTextView.text = args.article.source.name
        binding.newsDetailAuthorTextView.text = args.article.author
        binding.newsDetailContentTextView.text = args.article.content

        binding.newsDetailReadMoreBtn.setOnClickListener {
            val uri = Uri.parse(args.article.url)

            val bundle = Bundle()
            bundle.putBoolean("new_window", true)

            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}