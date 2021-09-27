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
- Renesas RZ/G2L smarc-rzg2l


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
git clone git://git.yoctoproject.org/meta-gplv2.git
git clone https://github.com/meta-qt5/meta-qt5.git
git clone https://github.com/renesas-rz/meta-rzg2.git
git clone https://github.com/renesas-rz/meta-renesas-ai.git
git clone https://github.com/renesas-rz/meta-renesas-ai-demos.git
```

2. Checkout specific versions
RZ/G2M:
```
cd $WORK/poky
git checkout -b tmp 7e7ee662f5dea4d090293045f7498093322802cc
cd $WORK/meta-openembedded
git checkout -b tmp 352531015014d1957d6444d114f4451e241c4d23
cd $WORK/meta-linaro
git checkout -b tmp 75dfb67bbb14a70cd47afda9726e2e1c76731885
cd $WORK/meta-gplv2
git checkout -b tmp f875c60ecd6f30793b80a431a2423c4b98e51548
cd $WORK/meta-qt5
git checkout -b tmp c1b0c9f546289b1592d7a895640de103723a0305
cd $WORK/meta-rzg2
git checkout -b tmp BSP-1.0.8
cd $WORK/meta-renesas-ai
git checkout -b tmp c12a82c9ccee6b38b86fb01c1ee4da6281970134
```

RZ/G2L:
```
cd $WORK/poky
git checkout -b tmp dunfell-23.0.5
cd $WORK/meta-openembedded
git checkout -b tmp cc6fc6b1641ab23089c1e3bba11e0c6394f0867c
cd $WORK/meta-gplv2
git checkout -b tmp 60b251c25ba87e946a0ca4cdc8d17b1cb09292ac
cd $WORK/meta-qt5
git checkout -b tmp c1b0c9f546289b1592d7a895640de103723a0305
cd $WORK/meta-rzg2
git checkout -b tmp b56a64c9badcb8cd43d286bbe0f57ac97fb465fc
cd $WORK/meta-renesas-ai
git checkout -b tmp c12a82c9ccee6b38b86fb01c1ee4da6281970134
```

3. Download proprietary software packages from RZ/G Marketplace
RZ/G2M:
```
(Evaluation version)
America: https://www.renesas.com/us/en/products/rzg-linux-platform/rzg-marcketplace/verified-linux-package/rzg2-mlp-eva.html
Europe: https://www.renesas.com/eu/en/products/rzg-linux-platform/rzg-marcketplace/verified-linux-package/rzg2-mlp-eva.html
Asia: https://www.renesas.com/sg/en/products/rzg-linux-platform/rzg-marcketplace/verified-linux-package/rzg2-mlp-eva.html
Japan: https://www.renesas.com/jp/ja/products/rzg-linux-platform/rzg-marcketplace/verified-linux-package/rzg2-mlp-eva.html
```

RZ/G2L:
```
America: https://www.renesas.com/us/en/products/microcontrollers-microprocessors/rz-arm-based-high-end-32-64-bit-mpus/rzg2l-general-purpose-microprocessors-dual-core-arm-cortex-a55-12-ghz-cpus-3d-graphics-and-video-codec
Europe: https://www.renesas.com/eu/en/products/microcontrollers-microprocessors/rz-arm-based-high-end-32-64-bit-mpus/rzg2l-general-purpose-microprocessors-dual-core-arm-cortex-a55-12-ghz-cpus-3d-graphics-and-video-codec
Asia: https://www.renesas.com/sg/en/products/microcontrollers-microprocessors/rz-arm-based-high-end-32-64-bit-mpus/rzg2l-general-purpose-microprocessors-dual-core-arm-cortex-a55-12-ghz-cpus-3d-graphics-and-video-codec
Japan: https://www.renesas.com/jp/ja/products/microcontrollers-microprocessors/rz-arm-based-high-end-32-64-bit-mpus/rzg2l-general-purpose-microprocessors-dual-core-arm-cortex-a55-12-ghz-cpus-3d-graphics-and-video-codec
```


4. Extract and copy proprietary libraries
RZ/G2M:
```
tar -C $WORK -zxf $WORK/RZG2_Group_*_Software_Package_for_Linux_*.tar.gz
export PKGS_DIR=$WORK/proprietary
cd $WORK/meta-rzg2
sh docs/sample/copyscript/copy_proprietary_softwares.sh -f $PKGS_DIR
unset PKGS_DIR
```

RZ/G2L:
```
unzip RTK0EF0045Z13001ZJ-v0.51_EN.zip
cd RTK0EF0045Z13001ZJ-v0.51_EN/proprietary
./copy_gfx_mmp.sh ../../
```


5. Execute source command
```
cd $WORK
source poky/oe-init-build-env
```

6. Copy build configuration files
RZ/G2M:
```
cp $WORK/meta-renesas-ai-demos/meta-shopping-basket-demo/templates/hihope-rzg2m/* $WORK/build/conf/
```

RZ/G2L:
```
cp $WORK/meta-renesas-ai-demos/meta-shopping-basket-demo/templates/smarc-rzg2l/* $WORK/build/conf/
```

7. (optional) Use the following commands in `$WORK/build/conf/local.conf` to edit the demo source version:
```
SHOPPING_BASKET_DEMO_REPO = "github.com/renesas-rz/rzg-shopping-basket-demo.git"
SHOPPING_BASKET_DEMO_REPO_PROTOCOL = "https"
SHOPPING_BASKET_DEMO_REPO_BRANCH = "master"
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

## Flashing instructions
### Partition and Format the SD
The SD card should be formatted to EXT4. Parted provides a terminal utility
to do this, alternatively Gnome Disks can be used from the Ubuntu GUI.

1. Install the tool
```
sudo apt update
sudo apt install parted
```

2. Identify the block device name for the SD Card, for example "/dev/sdc"
```
sudo fdisk -l
```

3. Create the partition table with an EXT4 partition
```
sudo parted /dev/sdc --script -- mklabel gpt
sudo parted /dev/sdc --script -- mkpart primary ext4 0% 100%
```

4. Format the partition to EXT4
```
sudo mkfs.ext4 -F /dev/sdc1
```

5. Confirm the partition table is set as expected
```
sudo parted /dev/sdc --script print
```

### Extract the Filesystem
Mount the root file system and extract it to the SD card
```
mount -t ext4 /dev/sdc1 /mnt/SD
sudo tar -xf core-image-qt-$PLATFORM.tar.gz -C /mnt/SD
```

### Add the Kernel Image and DTB
Copy the Kernel Image and DTB to the boot directory in the root filesystem
```
cp Image-$PLATFORM.bin Image-*.dtb /mnt/SD/boot/
```

### Using Wic Images
An alternative to the steps above is to use the Wic images yocto builds. A Wic
image provides a way to flash a bootable image with all the needed files.

From the host machine, flash the yocto generated Wic image with:
```
sudo bmaptool copy core-image-qt-$PLATFORM.wic.gz /dev/sdc --nobmap
```

Alernatively Windows GUI tools such as balenaEtcher can be used to flash
the SD card.

## Configuring the Platform
### Boot the Board
Make the following connections to the host machine:
* Serial

Make the following peripheral connections:
* Camera
* Mouse or USB touch
* HDMI
* Power

Then apply power to the board and enter U-Boot.

### Set U-Boot configuration environment
The U-Boot environment can be set from the U-boot terminal.

For the RZ/G2M:
```
setenv bootargs 'rw root=/dev/mmcblk0p1 rootwait'
setenv bootcmd 'ext4load mmc 0 0x48080000 Image-hihope-rzg2m.bin; ext4load mmc 0 0x48000000 Image-r8a774a1-hihope-rzg2m-ex.dtb; booti 0x48080000 - 0x48000000'
```

Finally, save the environment and boot:
```
saveenv
boot
```

Once Linux has booted, launch the demo from the terminal
```
/opt/shopping-basket-demo/shoppingbasket_demo_app
```

Alternatively, use the GUI buttons on the top left to start the demo.


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
* Click "About->Hardware" to read the board information.
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
