SUMMARY = "Shopping Basket Demo"
DESCRIPTION = "Build and install the RZ/G2 Object Detection Shopping Basket Demo application"

inherit populate_sdk_qt5
require recipes-qt/qt5/qt5.inc

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "qtmultimedia opencv gstreamer tensorflow-lite"

SHOPPING_DEMO_REPO ?= "github.com/renesas-rz/rzg-shopping-basket-demo.git"
SHOPPING_DEMO_REPO_PROTOCOL ?= "https"
SHOPPING_DEMO_REPO_BRANCH ?= "master"

SRC_URI = " \
	git://${SHOPPING_DEMO_REPO};protocol=${SHOPPING_DEMO_REPO_PROTOCOL};branch=${SHOPPING_DEMO_REPO_BRANCH} \
	file://sample_images/ \
	file://shoppingBasketDemo.tflite \
"
SRCREV = "${AUTOREV}"

SHOPPING_DEMO_INSTALL_DIRECTORY ?= "/opt/shopping-basket-demo"

S = "${WORKDIR}/git"

do_configure_prepend () {
	export SDKTARGETSYSROOT="../recipe-sysroot"
	export SHOPPING_DEMO_PLATFORM="${SHOPPING_DEMO_PLATFORM}"
	export SHOPPING_DEMO_INSTALL_DIRECTORY="${SHOPPING_DEMO_INSTALL_DIRECTORY}"
}

do_install_append () {
	install -d ${D}${SHOPPING_DEMO_INSTALL_DIRECTORY}
	install -d ${D}${SHOPPING_DEMO_INSTALL_DIRECTORY}/sample_images
	install -m 444 ${WORKDIR}/sample_images/* ${D}${SHOPPING_DEMO_INSTALL_DIRECTORY}/sample_images
	install -m 444 ${WORKDIR}/shoppingBasketDemo.tflite ${D}${SHOPPING_DEMO_INSTALL_DIRECTORY}
	install -m 555 ${B}/supermarket_demo_app ${D}${SHOPPING_DEMO_INSTALL_DIRECTORY}
}

FILES_${PN} = " \
	${SHOPPING_DEMO_INSTALL_DIRECTORY} \
"
