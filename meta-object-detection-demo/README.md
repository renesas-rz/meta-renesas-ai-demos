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
- Renesas RZ/G2H hihope-rzg2h from Hoperun
- Renesas RZ/G2M hihope-rzg2m from Hoperun
- Renesas RZ/G2E ek874 from Silicon Linux


## Build Instructions
**Build machine dependencies**
- Ubuntu 16.04 LTS
- Installed packages: gawk wget git-core diffstat unzip texinfo gcc-multilib
build-essential chrpath socat libsdl1.2-dev xterm cpio python python3
python3-pip python3-pexpect xz-utils debianutils iputils-ping

Before using this process, set the product ID and platform:

RZ/G2H:
```
export PLATFORM=hihope-rzg2h
```

RZ/G2M:
```
export PLATFORM=hihope-rzg2m
```

RZ/G2E:
```
export PLATFORM=ek874
```

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
git checkout -b tmp BSP-1.0.4-update1
cd $WORK/meta-renesas-ai
git checkout -b tmp v3.5.1
cd $WORK/meta-renesas-ai-demos
git checkout -b tmp OD_v3.0
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
cp $WORK/meta-renesas-ai-demos/meta-object-detection-demo/templates/$PLATFORM/* $WORK/build/conf/
```

7. (optional) Use the following commands in `$WORK/build/conf/local.conf` to edit the demo source version:
```
OBJECT_DETECTION_DEMO_REPO = "github.com/renesas-rz/rzg-object-detection-demo.git"
OBJECT_DETECTION_DEMO_REPO_PROTOCOL = "https"
OBJECT_DETECTION_DEMO_REPO_BRANCH = "master"
SRCREV_object-detection-demo = "7bb97be625c4bd5f3bbf2bfbc4e681d715c31b25" # Can be set to "${AUTOREV}" for the latest version.
```

8. Start build
```
bitbake core-image-qt
```

Once the build is completed, the Kernel, device tree and RFS are located in:
```
$WORK/build/tmp/deploy/images/$PLATFORM
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

For the RZ/G2H:
```
setenv bootargs 'rw root=/dev/mmcblk0p1 rootwait'
setenv bootcmd 'ext4load mmc 0 0x48080000 Image-hihope-rzg2h.bin; ext4load mmc 0 0x48000000 Image-r8a774e1-hihope-rzg2h-ex.dtb; booti 0x48080000 - 0x48000000'
```
For the RZ/G2M:
```
setenv bootargs 'rw root=/dev/mmcblk0p1 rootwait'
setenv bootcmd 'ext4load mmc 0 0x48080000 Image-hihope-rzg2m.bin; ext4load mmc 0 0x48000000 Image-r8a774a1-hihope-rzg2m-ex.dtb; booti 0x48080000 - 0x48000000'
```
For the RZ/G2E:
```
setenv bootargs 'rw root=/dev/mmcblk0p1 rootwait'
setenv bootcmd 'ext4load mmc 0 0x48080000 Image-ek874.bin; ext4load mmc 0 0x48000000 Image-r8a774c0-ek874.dtb; booti 0x48080000 - 0x48000000'
```

Finally, save the environment and boot:
```
saveenv
boot
```

Once Linux has booted, launch the demo from the terminal
```
/opt/object-detection-demo/object_detection_demo
```

Alternatively, use the GUI buttons on the top left to start the demo.

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
