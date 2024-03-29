<img src="https://user-images.githubusercontent.com/16231428/64536705-d7392380-d319-11e9-83ce-ff5e4c68717b.png">

<img align="left" src="https://user-images.githubusercontent.com/16231428/64536567-a0fba400-d319-11e9-9163-852037e3cffa.gif" width="260" height="460" /></a>
<a href="https://github.com/astrit-veliu/Smoolider">

<p><h1 align="left">SMOOLIDER ⭐</h1></p>

<h4>A smooth slider with a modern UI and effects based on ViewPager. More features coming soon</h4>

___

## Supports ❤
<p><h6>Feel free to give your support by contributing to this library. Buy me a beer!</h6>
<a href="https://paypal.me/AstritVeliu" >
<img src="https://img.shields.io/badge/Donate-PayPal-blue.svg" width="130" height="22"></a>
</p>
<p><h6>Follow me on Github for upcoming repositories:</h6>
<a href="https://github.com/astrit-veliu" >
<img src="https://user-images.githubusercontent.com/16231428/59339358-5069b000-8d04-11e9-9584-795700af65a0.PNG" width="140" height="32"></a></p>
</br>

![license](https://img.shields.io/badge/license-MIT%20License-blue.svg)
[![](https://jitpack.io/v/astrit-veliu/Boom.svg)](https://jitpack.io/#astrit-veliu/Smoolider)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/107ff292bd9f49b1a6824bf32f183e66)](https://www.codacy.com/manual/astrit-veliu/Smoolider?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=astrit-veliu/Smoolider&amp;utm_campaign=Badge_Grade)
[![CircleCI](https://circleci.com/gh/astrit-veliu/Smoolider.svg?style=svg)](https://circleci.com/gh/astrit-veliu/Smoolider)
<br>

## Installation

### build.gradle

Add `jitpack` maven in your root build.gradle at the end of repositories:
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add the dependency to your app `build.gradle` file (not your project build.gradle file).
```gradle
dependencies {
   implementation 'com.github.astrit-veliu:Smoolider:1.2'
}
```

## Usage
`Smoolider` is used the same as a normal ViewPager but more beautiful and with other functionalities. You can use my adapter, or implement a new one. 

### XML
Include the widget in xml.

```xml
<?xml version="1.0" encoding="UTF-8"?>

<com.av.smoothviewpager.Smoolider.SmoothViewpager
            android:id="@+id/smoolider"
            android:paddingRight="10dp"
            android:layout_alignParentBottom="true"
            android:clipToPadding = "false"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
	    </com.av.smoothviewpager.Smoolider.SmoothViewpager>
```

### Java
After attaching the adapter to SmoothViewpager you can start/cancel autoplay of slides, adjust the quality of images and interact with other widgets. (See the example for more details)
```Kotlin
        //simply call the setAdapter method with parameter a list of PageModel objects, where you can also add your own object into it
        viewpager.setAdapter(getDemoData())
        viewpager.setOnPageClick { page ->
            viewpager.removePage(page) //will remove the page object from Smoolider and jumps to first postion
	    viewpager.addPage(page) //will add the page object from Smoolider and jumps to its position
        }
        viewpager.setOnPageSelected { position, page ->
            //here you get the current position and the Page Object in case you want to do some actions with its data
        }
```

## 📄 License

Smoolider is released under the MIT license.
See [LICENSE](./LICENSE) for details.

```xml
MIT License

Copyright (c) 2019 Astrit Veliu

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
