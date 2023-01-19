package com.android.quizzy.presentation.profile

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.android.quizzy.R
import com.android.quizzy.ui.theme.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TakePicture(
    uiState: State<ProfileScreenState>,
    //   bitmap: Bitmap?,
    bottomSheetModalState: ModalBottomSheetState,
    //  saveImage : () -> Unit,
    navigator: DestinationsNavigator
) {
    var isCameraSelected by rememberSaveable { mutableStateOf(false) }
    //var imageUri: Uri? = null
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var bitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }
    var newPhoto = remember { mutableStateOf(false) }

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        bitmap = null
        Log.i("New", imageUri.toString())
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { btm: Bitmap? ->
        bitmap = btm
        imageUri = null
        Log.i("New uri", bitmap.toString())
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (isCameraSelected) {
                cameraLauncher.launch()
            } else {
                galleryLauncher.launch("image/*")
            }
            coroutineScope.launch {
                bottomSheetModalState.hide()
            }
        } else {
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(pastelBlue)
                    .padding(bottom = 30.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add Photo!",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        color = darkPastelBlue,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .background(darkPastelBlue)
                    )
                    Text(
                        text = "Take Photo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                when (PackageManager.PERMISSION_GRANTED) {
                                    ContextCompat.checkSelfPermission(
                                        context, Manifest.permission.CAMERA
                                    ) -> {
                                        cameraLauncher.launch()
                                        coroutineScope.launch {
                                            bottomSheetModalState.hide()
                                        }
                                    }
                                    else -> {
                                        isCameraSelected = true
                                        permissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                }
                            }
                            .padding(15.dp),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                    Divider(
                        modifier = Modifier
                            .height(0.5.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    )
                    Text(
                        text = "Choose from Gallery",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                when (PackageManager.PERMISSION_GRANTED) {
                                    ContextCompat.checkSelfPermission(
                                        context, Manifest.permission.READ_EXTERNAL_STORAGE
                                    ) -> {
                                        galleryLauncher.launch("image/*")
                                        coroutineScope.launch {
                                            bottomSheetModalState.hide()
                                        }
                                    }
                                    else -> {
                                        isCameraSelected = false
                                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    }
                                }
                            }
                            .padding(15.dp),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                    Divider(
                        modifier = Modifier
                            .height(0.5.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    )
                    Text(
                        text = "Cancel",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                coroutineScope.launch {
                                    bottomSheetModalState.hide()
                                }
                            }
                            .padding(15.dp),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
        },
        sheetState = bottomSheetModalState,

        modifier = Modifier
            .background(pastelBlue20)
    ) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp),
            shape = RectangleShape,

            colors = CardDefaults.elevatedCardColors(containerColor = brown80.copy(0.8F))
        ) {
            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { navigator.navigate("settings") }) {
                Text(
                    style = MaterialTheme.typography.labelMedium,
                    text = "Edit Profile",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Text(
                text = "${uiState.value.rank}",
                color = uiState.value.rank.color,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Card(
                modifier = Modifier
                    .padding(start = 100.dp, end = 100.dp)
                    .height(140.dp),

                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {


                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .align(Alignment.CenterHorizontally),

                    bitmap = bitmap?.asImageBitmap()
                        ?: ImageBitmap.imageResource(id = R.drawable.profile_pic),
                    contentDescription = "book",

                    )



                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            if (!bottomSheetModalState.isVisible) {
                                bottomSheetModalState.show()
                            } else {
                                bottomSheetModalState.hide()
                            }
                        }
                        //TODO  newPhoto.value = !newPhoto.value
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 24.dp, bottom = 8.dp)
                        .size(90.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "content description",
                        tint = yellowPastel
                    )
                }
            }


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(12.dp),
                textAlign = TextAlign.Center,
                text = uiState.value.username,
                style = MaterialTheme.typography.displaySmall
            )
            Divider(
                modifier = Modifier
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally), color = pastelBlack, thickness = 1.dp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                text = uiState.value.email,
                style = MaterialTheme.typography.bodyLarge
            )

        }


    }

    imageUri?.let {
        if (!isCameraSelected) {
            bitmap = if (Build.VERSION.SDK_INT < 28) {
                Timber.i("imageUri", bitmap.toString())
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)

            }
        }
        bitmap?.let { it1 ->
            Image(
                bitmap = it1.asImageBitmap(),
                contentDescription = "Image",
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
                    .padding(top = 10.dp),
                contentScale = ContentScale.Fit
            )
        }
        // saveImage(bitmap)
    }

    bitmap?.let { btm ->
        Image(
            bitmap = btm.asImageBitmap(),
            contentDescription = "Image",
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .padding(top = 10.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Fit
        )
    }
}