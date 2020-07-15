# meta-object-detection-demo
This OpenEmbedded/Yocto layer adds support for the Renesas FOSS (Free Open
Source Software) Object Detection Demo to the RZ/G2 series of Linux platforms.


This meta-layer builds the Object Detection Demo application using qmake. All
dependencies for the demo are also built and installed in the final filesystem.
The source code for the demo application can be found here:
**https://github.com/renesas-rz/rzg-object-detection-demo.git**


The demo is based on the Renesas RZ/G AI BSP which is published on GitHub:
**https://github.com/renesas-rz/meta-renesas-ai**


Supported Platforms:
- Renesas RZ/G2M hihope-rzg2m from Hoperun
- Renesas RZ/G2E ek874 from Silicon Linux


## Build Instructions
**Build machine dependencies**
- Ubuntu 16.04 LTS
- Installed packages: gawk wget git-core diffstat unzip texinfo gcc-multilib
build-essential chrpath socat libsdl1.2-dev xterm cpio python python3
python3-pip python3-pexpect xz-utils debianutils iputils-ping

1. Clone required repositories
```
export WORK=<path-to-your-build-directory>
mkdir -p $WORK
cd $WORK
git clone git://git.yoctoproject.org/poky.git
git clone git://git.openembedded.org/meta-openembedded.git
git clone git://git.linaro.org/openembedded/meta-linaro.git
git clone https://github.com/meta-qt5/meta-qt5.git
git clone https://github.com/renesas-rz/meta-rzg2.git
git clone https://github.com/renesas-rz/meta-renesas-ai.git
git clone https://github.com/renesas-rz/meta-renesas-ai-demos.git
```


2. Checkout specific versions
```
cd $WORK/poky
git checkout -b tmp 7e7ee662f5dea4d090293045f7498093322802cc
cd $WORK/meta-openembedded
git checkout -b tmp 352531015014d1957d6444d114f4451e241c4d23
cd $WORK/meta-linaro
git checkout -b tmp 75dfb67bbb14a70cd47afda9726e2e1c76731885
cd $WORK/meta-qt5
git checkout -b tmp c1b0c9f546289b1592d7a895640de103723a0305
cd $WORK/meta-rzg2
git checkout -b tmp BSP-1.0.1-update1
cd $WORK/meta-renesas-ai
git checkout -b tmp v3.2.0
cd $WORK/meta-renesas-ai-demos
git checkout -b tmp OD_v1.1
```

3. Download proprietary software packages from RZ/G Marketplace
```
(Evaluation version)
America: https://www.renesas.com/us/en/products/rzg-linux-platform/rzg-marcketplace/verified-linux-package/rzg2-mlp-eva.html
Europe: https://www.renesas.com/eu/en/products/rzg-linux-platform/rzg-marcketplace/verified-linux-package/rzg2-mlp-eva.html
Asia: https://www.renesas.com/sg/en/products/rzg-linux-platform/rzg-marcketplace/verified-linux-package/rzg2-mlp-eva.html
Japan: https://www.renesas.com/jp/ja/products/rzg-linux-platform/rzg-marcketplace/verified-linux-package/rzg2-mlp-eva.html
```

4. Extract and copy proprietary libraries
```
tar -C $WORK -zxf $WORK/RZG2_Group_*_Software_Package_for_Linux_*.tar.gz
export PKGS_DIR=$WORK/proprietary
cd $WORK/meta-rzg2
sh docs/sample/copyscript/copy_proprietary_softwares.sh -f $PKGS_DIR
unset PKGS_DIR
```


5. Execute source command
```
cd $WORK
source poky/oe-init-build-env
```


6. Copy build configuration files
```
# For hihope-rzg2m:
cp $WORK/meta-renesas-ai-demos/meta-object-detection-demo/templates/hihope-rzg2m/* $WORK/build/conf/
# For ek874:
cp $WORK/meta-renesas-ai-demos/meta-object-detection-demo/templates/ek874/* $WORK/build/conf/
```


7. (optional) Use the following commands in `$WORK/build/conf/local.conf` to edit the demo source:
```
OBJECT_DETECTION_DEMO_REPO = "github.com/renesas-rz/rzg-object-detection-demo.git"
OBJECT_DETECTION_DEMO_REPO_PROTOCOL = "https"
OBJECT_DETECTION_DEMO_REPO_BRANCH = "master"
SRCREV_object-detection-demo = "2e1e7d495fa8a8cc069db8cef7c725376b2c3eaf" # Can be set to "${AUTOREV}" for the latest version.
```


8. Start build
```
bitbake core-image-qt
```


Once the build is completed, the Kernel, device tree and RFS are located in:
```
# For hihope-rzg2m:
$WORK/build/tmp/deploy/images/hihope-rzg2m
# For ek874:
$WORK/build/tmp/deploy/images/ek874
```


## How to use the demo
```
Usage: ./object_detection_demo [options]
Object Detection Demo
  Draws boxes around detected objects and displays the name and
  confidence of the object. Also displays inference time.

Required Hardware:
  Camera: Currently supports Logitech C922 Pro Stream, should
          work with any USB camera that has a supported resolution
          of 800x600.
  TPU Mode: Requires Coral USB Accelerator.

Supported Models:
  MobileNet v2 SSD Quantised tflite

Buttons:
  Run: Run inference on the selected image once.
  Load Image: Load an image from the filesystem. Supported formats are
              bmp, jpg, and png.
  Load Webcam: Load a webcam stream.
  Capture Image: Capture an image from the webcam.
  Continuous Checkbox: Only available when a webcam stream is loaded.
                       Enable to continuously run inference.
  Stop: Stop continuous inference.
  Threads: Only available in CPU mode. Change the number of inference
           threads.
  About->License: Read the GPL v2 license that this app is licensed
                  under.
  Camera->Reset: Reset the connection to the webcam.
  Camera->Disconnect: Disconnect the currently connected webcam.

Default options:
  Camera: /dev/v4l/by-id/<first file>
  Label: ./*label*.txt
  Model:
    CPU Mode: ./mobilenet_ssd_v2_coco_quant_postprocess.tflite
    TPU Mode: ./mobilenet_ssd_v2_coco_quant_postprocess_edgetpu.tflite
  TPU Mode: Disabled

Options:
  -c, --camera <file>  Choose a camera.
  -l, --label <file>   Choose a label file.
  -m, --model <file>   Choose a model.
  -t, --tpu            Enable tpu processing.
  -h, --help           Displays this help.
```
