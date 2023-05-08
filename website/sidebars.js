const uiDescription = '꽥꽥의 핵심 모듈인 ui를 배워봅시다!'

/** @type {import('@docusaurus/plugin-content-docs').SidebarsConfig} */
const sidebars = {
  docs: [
    'introduction',
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
        'guides/documentation-style',
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
            'guides/ui/why-adopt',
            'guides/ui/setup',
            'guides/ui/mental-model',
            'guides/ui/components',
            'guides/ui/design-tokens',
            'guides/ui/decorators',
            'guides/ui/best-practices',
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
          label: 'aide',
          link: {
            type: 'doc',
            id: 'guides/aide/overview',
          },
          items: [
            'guides/aide/annotation',
            'guides/aide/processor',
          ],
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
    'development',
    'architecture',
    'modularization',
    'testing',
    'contributing',
    'resources',
    'releases',
    'license',
  ],
};
module.exports = sidebars;