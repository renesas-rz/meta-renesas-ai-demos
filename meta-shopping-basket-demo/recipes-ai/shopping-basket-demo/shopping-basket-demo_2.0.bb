SUMMARY = "Shopping Basket Demo"
DESCRIPTION = "Build and install the RZ/G2 Shopping Basket Demo application"

inherit populate_sdk_qt5
require recipes-qt/qt5/qt5.inc

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "qtmultimedia opencv gstreamer1.0 tensorflow-lite armnn"
RDEPENDS_${PN} = "libopencv-core libopencv-videoio libopencv-imgcodecs libopencv-imgproc armnn-dev"

SHOPPING_BASKET_DEMO_INSTALL_DIRECTORY ?= "/opt/shopping-basket-demo"

SHOPPING_BASKET_DEMO_REPO ?= "github.com/renesas-rz/rzg-shopping-basket-demo.git"
SHOPPING_BASKET_DEMO_REPO_PROTOCOL ?= "https"
SHOPPING_BASKET_DEMO_REPO_BRANCH ?= "master"

SRC_URI = " \
	git://${SHOPPING_BASKET_DEMO_REPO};protocol=${SHOPPING_BASKET_DEMO_REPO_PROTOCOL};branch=${SHOPPING_BASKET_DEMO_REPO_BRANCH};name=shopping-basket-demo \
	file://shoppingBasketDemo.tflite \
	file://icons/ \
	file://populate_scripts.sh \
"

SRCREV_shopping-basket-demo ?= "3dc78e4cc13a731e93a4627b7746feb37c9b90ea"

S = "${WORKDIR}/git"

do_configure_prepend () {
	export SDKTARGETSYSROOT="../recipe-sysroot"
}

do_install_append () {
	install -d ${D}${SHOPPING_BASKET_DEMO_INSTALL_DIRECTORY}
	install -m 444 ${WORKDIR}/shoppingBasketDemo.tflite ${D}${SHOPPING_BASKET_DEMO_INSTALL_DIRECTORY}
	install -m 555 ${B}/shoppingbasket_demo_app ${D}${SHOPPING_BASKET_DEMO_INSTALL_DIRECTORY}

	if echo "${BBLAYERS}" | grep -wq "meta-ai-demos-common" ; then
		install -d ${D}${SHOPPING_BASKET_DEMO_INSTALL_DIRECTORY}/icons
		install -d ${D}${SHOPPING_BASKET_DEMO_INSTALL_DIRECTORY}/scripts
		install -m 444 ${WORKDIR}/icons/* ${D}${SHOPPING_BASKET_DEMO_INSTALL_DIRECTORY}/icons
		${WORKDIR}/populate_scripts.sh ${SHOPPING_BASKET_DEMO_INSTALL_DIRECTORY}
		install -m 555 ${B}/scripts/* ${D}${SHOPPING_BASKET_DEMO_INSTALL_DIRECTORY}/scripts
	fi
}

FILES_${PN} = " \
	${SHOPPING_BASKET_DEMO_INSTALL_DIRECTORY} \
"

INSANE_SKIP_${PN} = "dev-deps dev-so"
