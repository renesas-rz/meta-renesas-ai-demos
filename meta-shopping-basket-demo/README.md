# meta-shopping-basket-demo
This OpenEmbedded/Yocto layer adds support for the Renesas FOSS (Free Open
Source Software) Object Detection Shopping Basket Demo to the RZ/G2M HiHope
Linux platform.


This meta-layer adds all dependencies and installs the Qt based demo application
into the final RFS. The demo itself is compiled seperately using qmake. The
source code for the demo application can be found here:
**https://github.com/renesas-rz/rzg-shopping-basket-demo.git**


The demo is based on the Renesas RZ/G AI BSP which is published on GitHub:
**https://github.com/renesas-rz/meta-renesas-ai**


Supported Platforms:
- Renesas RZ/G2M hihope-rzg2m


## Build Instructions
**Build machine dependencies**
- Ubuntu 16.04 LTS
- Installed packages: gawk wget git-core diffstat unzip texinfo gcc-multilib
build-essential chrpath socat cpio python python3 python3-pip python3-pexpect
xz-utils debianutils iputils-ping libsdl1.2-dev xterm


1. Clone required repositories
```
export WORK=<path-to-your-build-directory>
mkdir $WORK
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
git checkout -b tmp SB_v1.1
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
cp $WORK/meta-renesas-ai-demos/meta-shopping-basket-demo/templates/hihope-rzg2m/* $WORK/build/conf/
```


7. (optional) Use the following commands in `$WORK/build/conf/local.conf` to edit the demo source:
```
SHOPPING_DEMO_REPO = "github.com/renesas-rz/rzg-shopping-basket-demo.git"
SHOPPING_DEMO_REPO_PROTOCOL = "https"
SHOPPING_DEMO_REPO_BRANCH = "master"
SRCREV_shopping-basket-demo = "6351aa022b3692e5756693a31b1b2ec370b0af54" # Can be set to "${AUTOREV}" for the latest version.
```


8. Start build
```
bitbake core-image-qt
```


Once the build is completed, the Kernel, device tree and RFS are located in:
```
$WORK/build/tmp/deploy/images/hihope-rzg2m
```
## How to use the demo
* Click "Load Image" to load an image from the filesystem. Supported formats are
bmp, jpg, and png.
    * Inference is automatically run on the image and the results are displayed.
* Click "Load Webcam" to load a webcam stream.
    * Once the webcam stream has been loaded, the "Continuous" checkbox will be
available to check. Clicking on "Run" with the checkbox disabled will run
inference once, while clicking "Run" with the checkbox enabled will continuously
run inference on the webcam stream.
    * Clicking "Stop" will stop continuous inference if activated.
* Click "Capture Image" to capture an image from the webcam stream.
    * Inference is automatically run on the image and the results are displayed.
* Click the arrows on the "Threads" spinbox to dynamically change the number of
threads used for inference.
* Click "About->License" to read the GPLv2 license that this app is licensed
under.
* Click "Camera->Reset" to reset the connection to the webcam.
* Click "Camera->Disconnect" to disconnect the currently connected webcam."

Expected results:
* Boxes are drawn around the detected items.
* Names of detected items are shown in the top left-hand corner of each box.
* Percentage confidence is shown for each item.
* Names and prices for each item are shown in an alphabetical list on the
right-hand side of the application.
* Total cost is shown in the bottom right hand-side.
