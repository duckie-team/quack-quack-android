const lightCodeTheme = require('prism-react-renderer/themes/github');
const darkCodeTheme = require('prism-react-renderer/themes/dracula');

/** @type {import('@docusaurus/types').Config} */
const config = {
    title: 'QuackQuack',
    url: 'https://quackquack.duckie.team',
    baseUrl: '/android/',
    favicon: 'img/logo.ico',
    onBrokenMarkdownLinks: 'throw',
    tagline: 'Highly automated and Modifier-driven Jetpack Compose UI Kit',
    presets: [
        [
            '@docusaurus/preset-classic',
            {
                docs: {
                    routeBasePath: '/',
                    sidebarPath: require.resolve('./sidebars.js'),
                    editUrl: 'https://github.com/duckie-team/quack-quack-android/edit/main/website/',
                },
                theme: {
                    customCss: require.resolve('./src/css/custom.css'),
                },
            },
        ],
    ],
    themes: [
        [
            require.resolve("@easyops-cn/docusaurus-search-local"),
            /** @type {import("@easyops-cn/docusaurus-search-local").PluginOptions} */
            ({
                indexBlog: false,
                hashed: true,
                language: ["en", "ko"],
            }),
        ],
    ],
    themeConfig: {
        docs: {
            sidebar: {
                hideable: true,
            },
        },
        navbar: {
            title: 'QuackQuack',
            logo: {
                alt: 'QuackQuack Logo',
                src: 'img/logo.svg',
            },
            items: [
                {
                    href: 'https://github.com/duckie-team/quack-quack-android',
                    label: 'GitHub',
                    position: 'right',
                },
            ],
        },
        footer: {
            style: 'dark',
            copyright: `Copyright © ${new Date().getFullYear()} Duckie team. Built with Docusaurus.`,
        },
        prism: {
            theme: lightCodeTheme,
            darkTheme: darkCodeTheme,
            additionalLanguages: ['kotlin', 'bash'],
        },
    },
};

module.exports = config;
