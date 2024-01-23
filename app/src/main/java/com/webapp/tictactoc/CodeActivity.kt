package com.webapp.tictactoc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.webapp.tictactoc.databinding.ActivityCodeBinding

var isCodeMaker = true;
var code = "null";
var codeFound = false
var checkTemp = true
var keyValue: String = "null"

class CodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.Create.setOnClickListener {
            code = "null";
            codeFound = false
            checkTemp = true
            keyValue = "null"
            code = binding.GameCode.text.toString()
            binding.Create.visibility = View.GONE
            binding.Join.visibility = View.GONE
            binding.GameCode.visibility = View.GONE
            binding.textView4.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            if (code != "null" && code != null && code != "") {

                isCodeMaker = true;
                FirebaseDatabase.getInstance().reference.child("codes")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            var check = isValueAvailable(snapshot, code)

                            Handler().postDelayed({
                                if (check == true) {
                                    binding.Create.visibility = View.VISIBLE
                                    binding.Join.visibility = View.VISIBLE
                                    binding.GameCode.visibility = View.VISIBLE
                                    binding.textView4.visibility = View.VISIBLE
                                    binding.progressBar.visibility = View.GONE

                                } else {
                                    FirebaseDatabase.getInstance().reference.child("codes").push()
                                        .setValue(code)
                                    isValueAvailable(snapshot, code)
                                    checkTemp = false
                                    Handler().postDelayed({
                                        accepted()
                                        errorMsg("Please don't go back")
                                    }, 300)

                                }
                            }, 2000)


                        }

                    })
            } else {
                binding.Create.visibility = View.VISIBLE
                binding.Join.visibility = View.VISIBLE
                binding.GameCode.visibility = View.VISIBLE
                binding.textView4.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                errorMsg("Enter Code Properly")
            }
        }
        binding.Join.setOnClickListener {
            code = "null";
            codeFound = false
            checkTemp = true
            keyValue = "null"
            code =    binding.GameCode.text.toString()
            if (code != "null" && code != null && code != "") {
                binding.Create.visibility = View.GONE
                binding.Join.visibility = View.GONE
                binding.GameCode.visibility = View.GONE
                binding.textView4.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                isCodeMaker = false;
                FirebaseDatabase.getInstance().reference.child("codes")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            var data: Boolean = isValueAvailable(snapshot, code)

                            Handler().postDelayed({
                                if (data == true) {
                                    codeFound = true
                                    accepted()
                                    binding.Create.visibility = View.VISIBLE
                                    binding.Join.visibility = View.VISIBLE
                                    binding. GameCode.visibility = View.VISIBLE
                                    binding.textView4.visibility = View.VISIBLE
                                    binding.progressBar.visibility = View.GONE
                                } else {
                                    binding.Create.visibility = View.VISIBLE
                                    binding.Join.visibility = View.VISIBLE
                                    binding.GameCode.visibility = View.VISIBLE
                                    binding.textView4.visibility = View.VISIBLE
                                    binding.progressBar.visibility = View.GONE
                                    errorMsg("Invalid Code")
                                }
                            }, 2000)


                        }


                    })

            } else {
                errorMsg("Enter Code Properly")
            }
        }

    }

    fun accepted() {
        startActivity(Intent(this, ThirdPage::class.java));
        binding.Create.visibility = View.VISIBLE
        binding.Join.visibility = View.VISIBLE
        binding.GameCode.visibility = View.VISIBLE
        binding.textView4.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

    }

    fun errorMsg(value: String) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }

    fun isValueAvailable(snapshot: DataSnapshot, code: String): Boolean {
        var data = snapshot.children
        data.forEach {
            var value = it.getValue().toString()
            if (value == code) {
                keyValue = it.key.toString()
                return true;
            }
        }
        return false
    }
}
