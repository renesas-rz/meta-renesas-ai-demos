# meta-shopping-basket-demo
This OpenEmbedded/Yocto layer adds support for the Renesas FOSS (Free Open
Source Software) Shopping Basket Demo to the RZ/G2E ek874 and RZ/G2M
hihope-rzg2m Linux platforms, as well as the RZ/G2L and RZ/G2LC SMARC evaluation
kits.

This meta-layer adds all dependencies and installs the Qt based demo application
into the final RFS. The demo itself can be compiled seperately using qmake. The
source code for the demo application can be found here:
**https://github.com/renesas-rz/rzg-shopping-basket-demo.git**


The demo is based on the Renesas RZ/G AI BSP which is published on GitHub:
**https://github.com/renesas-rz/meta-renesas-ai**


Supported Platforms:
- Renesas RZ/G2E ek874
- Renesas RZ/G2M hihope-rzg2m
- Renesas RZ/G2L smarc-rzg2l
- Renesas RZ/G2LC smarc-rzg2lc

## Build Instructions
**Build machine dependencies**
- Ubuntu 20.04 LTS (RZ/G2L) or Ubuntu 18.04 (RZ/G2E and RZ/G2M)
- Installed packages: gawk wget git-core diffstat unzip texinfo gcc-multilib
build-essential chrpath socat cpio python3 python3-pip python3-pexpect
xz-utils debianutils iputils-ping python3-git python3-jinja2
libegl1-mesa libsdl1.2-dev pylint3 xterm

1. Clone required repositories
```
export WORK=<path-to-your-build-directory>
mkdir -p $WORK
cd $WORK
git clone git://git.yoctoproject.org/poky.git
git clone git://git.openembedded.org/meta-openembedded.git
git clone git://git.yoctoproject.org/meta-gplv2.git
git clone https://github.com/meta-qt5/meta-qt5.git
git clone https://github.com/renesas-rz/meta-rzg2.git
git clone https://github.com/renesas-rz/meta-renesas-ai.git
git clone https://github.com/renesas-rz/meta-renesas-ai-demos.git
```

RZ/G2E and RZ/G2M:
```
git clone git://git.linaro.org/openembedded/meta-linaro.git
```

2. Checkout specific versions

RZ/G2E and RZ/G2M:
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
git checkout -b tmp v4.4.0
```

RZ/G2L and RZ/G2LC:
```
cd $WORK/poky
git checkout -b tmp dunfell-23.0.5
git cherry-pick 9e444
cd $WORK/meta-openembedded
git checkout -b tmp cc6fc6b1641ab23089c1e3bba11e0c6394f0867c
cd $WORK/meta-gplv2
git checkout -b tmp 60b251c25ba87e946a0ca4cdc8d17b1cb09292ac
cd $WORK/meta-qt5
git checkout -b tmp c1b0c9f546289b1592d7a895640de103723a0305
cd $WORK/meta-rzg2
git checkout -b tmp rzg2l_bsp_v1.3-update1
cd $WORK/meta-renesas-ai
git checkout -b tmp 3a8a259291e08544b5d916f3f81f82747425b488
cd $WORK/meta-rzg2
git am $WORK/meta-renesas-ai/patches/meta-rzg2/dunfell-rzg2l/0001-Enable-RZ-G2L-Qt-SDK-builds.patch
```

3. Download proprietary software packages from RZ/G website

RZ/G2E and RZ/G2M:
```
America: https://www.renesas.com/us/en/products/rzg-linux-platform/rzg-marcketplace/verified-linux-package/rzg2-mlp-eva.html
Europe: https://www.renesas.com/eu/en/products/rzg-linux-platform/rzg-marcketplace/verified-linux-package/rzg2-mlp-eva.html
Asia: https://www.renesas.com/sg/en/products/rzg-linux-platform/rzg-marcketplace/verified-linux-package/rzg2-mlp-eva.html
Japan: https://www.renesas.com/jp/ja/products/rzg-linux-platform/rzg-marcketplace/verified-linux-package/rzg2-mlp-eva.html
```

RZ/G2L and RZ/G2LC:
America: [RTK0EF0045Z13001ZJ-v0.8_EN.zip](https://www.renesas.com/us/en/products/microcontrollers-microprocessors/rz-arm-based-high-end-32-64-bit-mpus/rzg2l-mali-graphic-library-evaluation-version), [RTK0EF0045Z15001ZJ-v0.51_EN.zip](https://www.renesas.com/us/en/software-tool/rzg2l-video-codec-library-evaluation-version-v051)  
Europe: [RTK0EF0045Z13001ZJ-v0.8_EN.zip](https://www.renesas.com/eu/en/products/microcontrollers-microprocessors/rz-arm-based-high-end-32-64-bit-mpus/rzg2l-mali-graphic-library-evaluation-version), [RTK0EF0045Z15001ZJ-v0.51_EN.zip](https://www.renesas.com/eu/en/software-tool/rzg2l-video-codec-library-evaluation-version-v051)  
Asia: [RTK0EF0045Z13001ZJ-v0.8_EN.zip](https://www.renesas.com/sg/en/products/microcontrollers-microprocessors/rz-arm-based-high-end-32-64-bit-mpus/rzg2l-mali-graphic-library-evaluation-version), [RTK0EF0045Z15001ZJ-v0.51_EN.zip](https://www.renesas.com/sg/en/software-tool/rzg2l-video-codec-library-evaluation-version-v051)  
Japan: [RTK0EF0045Z13001ZJ-v0.8_EN.zip](https://www.renesas.com/jp/ja/products/microcontrollers-microprocessors/rz-arm-based-high-end-32-64-bit-mpus/rzg2l-mali-graphic-library-evaluation-version), [RTK0EF0045Z15001ZJ-v0.51_EN.zip](https://www.renesas.com/jp/ja/software-tool/rzg2l-video-codec-library-evaluation-version-v051)  


4. Add the proprietary libraries

RZ/G2E and RZ/G2M:
```
tar -C $WORK -zxf $WORK/RZG2_Group_*_Software_Package_for_Linux_*.tar.gz
export PKGS_DIR=$WORK/proprietary
cd $WORK/meta-rzg2
sh docs/sample/copyscript/copy_proprietary_softwares.sh -f $PKGS_DIR
unset PKGS_DIR
```

RZ/G2L and RZ/G2LC:
```
cd $WORK
unzip RTK0EF0045Z15001ZJ-v0.51_EN.zip
unzip RTK0EF0045Z13001ZJ-v0.8_EN.zip
tar -xf RTK0EF0045Z15001ZJ-v0.51_EN/meta-rz-features.tar.gz
tar -xf RTK0EF0045Z13001ZJ-v0.8_EN/meta-rz-features.tar.gz
```

5. Execute source command
```
cd $WORK
source poky/oe-init-build-env
```

6. Copy build configuration files

RZ/G2E:
```
cp $WORK/meta-renesas-ai-demos/meta-shopping-basket-demo/templates/ek874/* $WORK/build/conf/
```

RZ/G2M:
```
cp $WORK/meta-renesas-ai-demos/meta-shopping-basket-demo/templates/hihope-rzg2m/* $WORK/build/conf/
```

RZ/G2L:
```
cp $WORK/meta-renesas-ai-demos/meta-shopping-basket-demo/templates/smarc-rzg2l/* $WORK/build/conf/
```
RZ/G2LC:
```
cp $WORK/meta-renesas-ai-demos/meta-shopping-basket-demo/templates/smarc-rzg2lc/* $WORK/build/conf/
```

Enable the `LICENSE_FLAGS_WHITELIST` line in local.conf.


7. (optional) Use the following commands in `$WORK/build/conf/local.conf` to edit the demo source version:
```
SHOPPING_BASKET_DEMO_REPO = "github.com/renesas-rz/rzg-shopping-basket-demo.git"
SHOPPING_BASKET_DEMO_REPO_PROTOCOL = "https"
SHOPPING_BASKET_DEMO_REPO_BRANCH = "<branch_name>"
SRCREV_shopping-basket-demo = "<specific_commit_sha>" # Can be set to "${AUTOREV}" for the latest version.
```

8. Start build
```
bitbake core-image-qt
```

Once the build is completed, the Kernel, device tree and RFS are located in:

RZ/G2E
```
$WORK/build/tmp/deploy/images/ek874
```

RZ/G2M
```
$WORK/build/tmp/deploy/images/hihope-rzg2m
```

RZ/G2L
```
$WORK/build/tmp/deploy/images/smarc-rzg2l
```

RZ/G2LC
```
$WORK/build/tmp/deploy/images/smarc-rzg2lc
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
* [Coral OV5645 MIPI-CSI Camera](https://coral.ai/products/camera/) (RZ/G2L and RZ/G2LC)
* OmniVision OV5645 Mipi-CSI Camera (RZ/G2M and RZ/G2E)
* Mouse or USB touch
* HDMI
* Power

Then apply power to the board and enter U-Boot.

### Set U-Boot configuration environment
The U-Boot environment can be set from the U-boot terminal.

RZ/G2E:
```
setenv bootargs 'rw root=/dev/mmcblk0p1 rootwait'
setenv bootcmd 'ext4load mmc 0 0x48080000 Image-ek874.bin; ext4load mmc 0 0x48000000 Image-r8a774c0-ek874.dtb; booti 0x48080000 - 0x48000000'
```

RZ/G2M:
```
setenv bootargs 'rw root=/dev/mmcblk0p1 rootwait'
setenv bootcmd 'ext4load mmc 0 0x48080000 Image-hihope-rzg2m.bin; ext4load mmc 0 0x48000000 Image-r8a774a1-hihope-rzg2m-ex-mipi-2.1.dtb; booti 0x48080000 - 0x48000000'
```

RZ/G2L:
```
setenv bootargs 'rw root=/dev/mmcblk1p1 rootwait'
setenv bootcmd 'ext4load mmc 0 0x48080000 Image-smarc-rzg2l; ext4load mmc 0 0x48000000 Image-r9a07g044l2-smarc.dtb; booti 0x48080000 - 0x48000000'
```

RZ/G2LC:
```
setenv bootargs 'rw root=/dev/mmcblk1p1 rootwait'
setenv bootcmd 'ext4load mmc 0 0x48080000 Image-smarc-rzg2lc.bin; ext4load mmc 0 0x48000000 r9a07g044c2-smarc-rzg2lc.dtb; booti 0x48080000 - 0x48000000'
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
* Click "Process Basket" to capture an image from the webcam stream.
    * Inference is automatically run on the image and the results are displayed.
* Click "Next Basket" to clear inference results and resume live camera feed.
* Click "About->Hardware" to read the board information.
* Click "About->License" to read the GPLv2 license that this app is licensed
under.
* Click "About->Exit" to close the application.
* Click "Inference->Enable/Disable ArmNN Delegate" to enable or disable ArmNN
Delegate during inference.

Expected results:
* Boxes are drawn around the detected items.
* Names of detected items are shown in the top left-hand corner of each box.
* Percentage confidence is shown for each item.
* Names and prices for each item are shown in an alphabetical list on the
right-hand side of the application.
* Total cost is shown in the bottom right hand-side.
* Total items and inference time is shown in the top right-hand corner of
the application.


## Notes
### OV5645 camera sensor
The CMOS camera sensor (OV5645) is no longer available, and should not be used
for mass production.  
Any software support provided is for evaluation purposes only.
