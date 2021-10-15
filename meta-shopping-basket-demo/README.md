# meta-shopping-basket-demo
This OpenEmbedded/Yocto layer adds support for the Renesas FOSS (Free Open
Source Software) Shopping Basket Demo to the RZ/G2L SMARC evaluation kit.

This meta-layer adds all dependencies and installs the Qt based demo application
into the final RFS. The demo itself can be compiled seperately using qmake. The
source code for the demo application can be found here:
**https://github.com/renesas-rz/rzg-shopping-basket-demo.git**


The demo is based on the Renesas RZ/G AI BSP which is published on GitHub:
**https://github.com/renesas-rz/meta-renesas-ai**


Supported Platforms:
- Renesas RZ/G2L smarc-rzg2l


## Build Instructions
**Build machine dependencies**
- Ubuntu 20.04 LTS
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

2. Checkout specific versions

RZ/G2L:
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
git checkout -b tmp rzg2l_bsp_v1.1-update1
cd $WORK/meta-renesas-ai
git checkout -b tmp c12a82c9ccee6b38b86fb01c1ee4da6281970134
cd $WORK/meta-rzg2
git am $WORK/meta-renesas-ai/patches/meta-rzg2/dunfell-rzg2l/0001-cip-core.inc-Fix-recipes-debian-BBMASK.patch
git am $WORK/meta-renesas-ai-demos/patches/meta-rzg2/dunfell-rzg2l/0001-Enable-RZ-G2L-Qt-SDK-builds.patch
```

3. Download proprietary software packages from RZ/G website
RZ/G2L:
```
America: https://www.renesas.com/us/en/products/microcontrollers-microprocessors/rz-arm-based-high-end-32-64-bit-mpus/rzg2l-general-purpose-microprocessors-dual-core-arm-cortex-a55-12-ghz-cpus-3d-graphics-and-video-codec
Europe: https://www.renesas.com/eu/en/products/microcontrollers-microprocessors/rz-arm-based-high-end-32-64-bit-mpus/rzg2l-general-purpose-microprocessors-dual-core-arm-cortex-a55-12-ghz-cpus-3d-graphics-and-video-codec
Asia: https://www.renesas.com/sg/en/products/microcontrollers-microprocessors/rz-arm-based-high-end-32-64-bit-mpus/rzg2l-general-purpose-microprocessors-dual-core-arm-cortex-a55-12-ghz-cpus-3d-graphics-and-video-codec
Japan: https://www.renesas.com/jp/ja/products/microcontrollers-microprocessors/rz-arm-based-high-end-32-64-bit-mpus/rzg2l-general-purpose-microprocessors-dual-core-arm-cortex-a55-12-ghz-cpus-3d-graphics-and-video-codec
```

4. Extract and copy proprietary libraries
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
RZ/G2L:
```
cp $WORK/meta-renesas-ai-demos/meta-shopping-basket-demo/templates/smarc-rzg2l/* $WORK/build/conf/
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
```
$WORK/build/tmp/deploy/images/smarc-rzg2l
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
* [Coral OV5645 MIPI-CSI Camera](https://coral.ai/products/camera/)
* Mouse or USB touch
* HDMI
* Power

Then apply power to the board and enter U-Boot.

### Set U-Boot configuration environment
The U-Boot environment can be set from the U-boot terminal.

RZ/G2L:
```
setenv bootargs 'rw root=/dev/mmcblk1p1 rootwait'
setenv bootcmd 'ext4load mmc 0 0x48080000 Image-smarc-rzg2l; ext4load mmc 0 0x48000000 Image-r9a07g044l2-smarc.dtb; booti 0x48080000 - 0x48000000'
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
