#!/bin/sh

WESTON_FILE=${1}
DESKTOP_INSTALL_DIR=${2}

cat >> "${WESTON_FILE}" <<-EOF

[output]
name=HDMI-A-1
mode=74.25 1280 1390 1430 1650 720 725 730 750 -hsync +vsync
EOF
