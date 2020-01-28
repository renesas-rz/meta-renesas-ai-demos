# meta-object-detection-demo-env
This OpenEmbedded/Yocto layer adds a desktop background and icon launchers to
the RZ/G2 reference platforms.

Supported Platforms:
- Renesas RZ/G2M hihope-rzg2m from Hoperun
- Renesas RZ/G2E ek874 from Silicon Linux


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
git checkout -b tmp master
```

3. Download proprietary software packages from RZ/G Marketplace
```
(Evaluation version)
America: https://mp.renesas.com/en-us/rzg/marketplace/linux_package/rzg2-mlp-eva.html
Europe: https://mp.renesas.com/en-eu/rzg/marketplace/linux_package/rzg2-mlp-eva.html
Asia: https://mp.renesas.com/en-sg/rzg/marketplace/linux_package/rzg2-mlp-eva.html
Japan: https://mp.renesas.com/ja-jp/rzg/marketplace/linux_package/rzg2-mlp-eva.html
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
cp $WORK/meta-renesas-ai-demos/meta-ai-demos-common/templates/<board-name>/* $WORK/build/conf/
```


7. Start build
```
bitbake core-image-qt
```


Once the build is completed, the Kernel, device tree and RFS are located in:
```
$WORK/build/tmp/deploy/images/<board-name>
```
