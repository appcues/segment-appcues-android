# Analytics-Kotlin Appcues
[![maven](https://img.shields.io/maven-central/v/com.appcues/segment-appcues)](https://repo1.maven.org/maven2/com/appcues/segment-appcues/)
[![License: MIT](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/appcues/segment-appcues-android/blob/main/LICENSE)

Add Appcues device mode support to your applications via this plugin for [Analytics-Kotlin](https://github.com/segmentio/analytics-kotlin)


## Adding the dependency

To install the Segment-Appcues integration, simply add this line to your gradle file:

```
implementation 'com.appcues:segment-appcues:<latest_version>'
```

Or the following for Kotlin DSL

```
implementation("com.appcues:segment-appcues:<latest_version>")
```

An installation [tutorial video](https://appcues.wistia.com/medias/x2favz5qnu) is also available, for reference.

## Using the Plugin in your App

Open the file where you setup and configure the Analytics-Kotlin library.  Add this plugin to the list of imports.

```
import com.appcues.segment.AppcuesDestination
```

Just under your Analytics-Kotlin library setup, call `analytics.add(plugin = ...)` to add an instance of the plugin to the Analytics timeline.

```
    analytics = Analytics("<YOUR WRITE KEY>", applicationContext) {
        this.flushAt = 3
        this.trackApplicationLifecycleEvents = true
    }
    analytics.add(plugin = AppcuesDestination(applicationContext))
```

Your events will now begin to flow to Appcues in device mode.

## Supporting Builder Preview and Screen Capture

During installation, follow the steps outlined in in the Appcues Android SDK documentation for [Configuring the Appcues URL Scheme](https://github.com/appcues/appcues-android-sdk/blob/main/docs/URLSchemeConfiguring.md). This is necessary for the complete Appcues builder experience, supporting experience preview, screen capture and debugging.

## Support

Please use Github issues, Pull Requests, or feel free to reach out to our [support team](mailto:support@appcues.com).


## License
```
MIT License

Copyright (c) 2022 Appcues

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
