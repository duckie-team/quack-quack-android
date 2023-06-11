/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

const lightCodeTheme = require('prism-react-renderer/themes/github');
const darkCodeTheme = require('prism-react-renderer/themes/dracula');

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'QuackQuack',
  url: 'https://quackquack.duckie.team',
  baseUrl: '/android/',
  favicon: 'images/logo.ico',
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
          showLastUpdateAuthor: true,
          showLastUpdateTime: true,
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
    image: 'images/meta-banner.png',
    docs: {
      sidebar: {
        hideable: true,
      },
    },
    navbar: {
      title: 'QuackQuack',
      hideOnScroll: true,
      logo: {
        alt: 'QuackQuack Logo',
        src: 'images/logo.svg',
      },
      items: [
        {
          href: 'https://github.com/duckie-team/quack-quack-android',
          label: 'GitHub',
          position: 'right',
        },
        {
          href: 'https://quackquack.duckie.team/android/api',
          label: 'API',
          position: 'right',
        },
      ],
    },
    footer: {
      style: 'dark',
      logo: {
        alt: 'Duckie Developer',
        src: 'images/duckie-developer.svg',
        href: 'https://github.com/duckie-team',
      },
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
