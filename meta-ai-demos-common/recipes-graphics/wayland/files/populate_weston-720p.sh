#!/bin/sh

WESTON_FILE=${1}

sed -i '/name=HDMI-A-1/{n;s/.*/mode=74.25 1280 1390 1430 1650 720 725 730 750 -hsync +vsync/;}' "${WESTON_FILE}"
