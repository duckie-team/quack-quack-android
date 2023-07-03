/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

const uiDescription = '꽥꽥의 핵심 모듈인 ui를 배워봅시다!'

/** @type {import('@docusaurus/plugin-content-docs').SidebarsConfig} */
const sidebars = {
  docs: [
    'introduction',
    'why-adopt',
    'mental-model',
    {
      type: 'category',
      label: 'Guides',
      link: {
        type: 'generated-index',
        title: 'QuackQuack Guides',
        description: '꽥꽥의 다양한 기능을 배워봅시다!',
        keywords: ['guides'],
      },
      items: [
        'guides/runtime',
        'guides/material',
        'guides/animation',
        {
          type: 'category',
          label: 'ui',
          description: uiDescription,
          link: {
            type: 'generated-index',
            title: 'QuackQuack UI',
            description: uiDescription,
            keywords: ['ui'],
          },
          items: [
            'guides/ui/components',
            'guides/ui/design-tokens',
            'guides/ui/decorators',
          ],
        },
        {
          type: 'category',
          label: 'sugar',
          link: {
            type: 'doc',
            id: 'guides/sugar/overview',
          },
          items: ['guides/sugar/material'],
        },
        {
          type: 'category',
          label: 'casa',
          link: {
            type: 'doc',
            id: 'guides/casa/overview',
          },
          items: [
            'guides/casa/annotation',
            'guides/casa/material',
            'guides/casa/processor',
          ],
        },
        {
          type: 'category',
          label: 'utils',
          link: {
            type: 'generated-index',
            title: 'QuackQuack Utils',
            description: '꽥꽥은 개발자의 편의를 위해 다양한 유틸을 제공합니다.',
            keywords: ['utilities'],
          },
          items: [
            'guides/util/common',
            'guides/util/backend',
          ],
        },
      ],
    },
    'best-practices',
    'development',
    'architecture',
    'modularization',
    'testing',
    'contributing',
    'resources',
    {
      type: 'category',
      label: 'Releases',
      link: {
        type: 'generated-index',
        title: 'QuackQuack Releases',
        description: 'Every release includes a BOM updates. Check the GitHub release page for the BOM version.',
        keywords: ['release note'],
      },
      items: [
        'relnotes/material',
        'relnotes/material-icon',
        'relnotes/animation',
        'relnotes/ui',
        'relnotes/ui-plugin',
        'relnotes/ui-plugin-image',
        'relnotes/ui-plugin-image-gif',
        'relnotes/ui-plugin-interceptor',
        'relnotes/ui-plugin-interceptor-textfield',
      ],
    },
    'license',
  ],
};
module.exports = sidebars;
