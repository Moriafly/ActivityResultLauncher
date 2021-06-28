/*
 * Copyright (c) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.dylanc.activityresult.launcher

import android.Manifest
import android.net.Uri
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import com.dylanc.callbacks.Callback0
import com.dylanc.callbacks.Callback2
import java.io.File

/**
 * @author Dylan Cai
 */
class GetContentLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<String, Uri>(caller, GetContent()) {

  private val requestPermissionLauncher = RequestPermissionLauncher(caller)

  @JvmOverloads
  fun launch(
    input: String,
    onActivityResult: ActivityResultCallback<Uri?>,
    onPermissionDenied: Callback0,
    onExplainRequestPermission: Callback0? = null
  ) {
    requestPermissionLauncher.launch(
      Manifest.permission.READ_EXTERNAL_STORAGE,
      onGranted = { launch(input, onActivityResult) },
      onPermissionDenied,
      onExplainRequestPermission
    )
  }

  fun launch(input: String?, onActivityResult: Callback2<Uri?, File?>) {
    launch(input) { uri ->
      if (uri != null) {
        onActivityResult(uri, uri.copyToCacheFile(context))
      } else {
        onActivityResult(null, null)
      }
    }
  }

  @JvmOverloads
  fun launch(
    input: String,
    onActivityResult: Callback2<Uri?, File?>,
    onPermissionDenied: Callback0,
    onExplainRequestPermission: Callback0? = null
  ) {
    requestPermissionLauncher.launch(
      Manifest.permission.READ_EXTERNAL_STORAGE,
      onGranted = { launch(input, onActivityResult) },
      onPermissionDenied,
      onExplainRequestPermission
    )
  }

  @JvmOverloads
  fun launchForImage(
    onActivityResult: ActivityResultCallback<Uri?>,
    onPermissionDenied: Callback0,
    onExplainRequestPermission: Callback0? = null
  ) = launch("image/*", onActivityResult, onPermissionDenied, onExplainRequestPermission)

  @JvmOverloads
  fun launchForImage(
    onActivityResult: Callback2<Uri?, File?>,
    onPermissionDenied: Callback0,
    onExplainRequestPermission: Callback0? = null
  ) = launch("image/*", onActivityResult, onPermissionDenied, onExplainRequestPermission)

  @JvmOverloads
  fun launchForVideo(
    onActivityResult: ActivityResultCallback<Uri?>,
    onPermissionDenied: Callback0,
    onExplainRequestPermission: Callback0? = null
  ) = launch("video/*", onActivityResult, onPermissionDenied, onExplainRequestPermission)

  @JvmOverloads
  fun launchForVideo(
    onActivityResult: Callback2<Uri?, File?>,
    onPermissionDenied: Callback0,
    onExplainRequestPermission: Callback0? = null
  ) = launch("video/*", onActivityResult, onPermissionDenied, onExplainRequestPermission)

  @JvmOverloads
  fun launchForAudio(
    onActivityResult: ActivityResultCallback<Uri?>,
    onPermissionDenied: Callback0,
    onExplainRequestPermission: Callback0? = null
  ) = launch("audio/*", onActivityResult, onPermissionDenied, onExplainRequestPermission)

  @JvmOverloads
  fun launchForAudio(
    onActivityResult: Callback2<Uri?, File?>,
    onPermissionDenied: Callback0,
    onExplainRequestPermission: Callback0? = null
  ) = launch("audio/*", onActivityResult, onPermissionDenied, onExplainRequestPermission)
}