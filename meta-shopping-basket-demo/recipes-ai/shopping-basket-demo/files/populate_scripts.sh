#!/bin/sh

INSTALL_DIRECTORY=${1}

mkdir -p scripts

cat > "scripts/shopping-basket-demo.sh" <<-EOF
#!/bin/sh

cd ${INSTALL_DIRECTORY}
./shoppingbasket_demo_app
EOF
