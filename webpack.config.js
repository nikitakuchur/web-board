const path = require('path');

const MiniCssExtractPlugin = require('mini-css-extract-plugin');

module.exports = {
    entry: './src/main/js/App.js',
    output: {
        path: path.resolve(__dirname, 'src/main/webapp/static/dist'),
        filename: 'bundle.js',
    },
    plugins: [
        new MiniCssExtractPlugin(),
    ],
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                use: ['babel-loader'],
            },
            {
                test: /\.css$/i,
                use: [MiniCssExtractPlugin.loader, 'css-loader']
            },
            {
                test: /\.(png|jpe?g|gif)$/i,
                loader: 'file-loader',
                options: {
                    publicPath: 'static/dist',
                }
            },
        ],
    },
    resolve: {
        extensions: ['.js', '.jsx'],
    },
    mode: "development",
    devtool: "source-map"
};
