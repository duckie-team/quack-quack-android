/** @type {import('@docusaurus/plugin-content-docs').SidebarsConfig} */
const sidebars = {
    docs: [
        'Introduction',
        {
            type: 'category',
            label: 'Guides',
            link: {
                type: 'generated-index',
                title: 'QuackQuack Guides',
                description: 'QuackQuack의 다양한 기능을 배워봅시다!',
                keywords: ['guides'],
                image: '/img/banner.svg',
            },
            items: [
                'guides/documentation-style',
                'guides/runtime',
                'guides/material',
                'guides/animation',
                {
                    type: 'category',
                    label: 'ui',
                    link: {
                        type: 'generated-index',
                        title: 'QuackQuack UI',
                        description: 'QuackQuack의 핵심 모듈인 ui를 배워봅시다!',
                        keywords: ['ui'],
                    },
                    items: [
                        'guides/ui/mental-model',
                        'guides/ui/components',
                        'guides/ui/tokens',
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
                        description: 'QuackQuack은 개발자의 편의를 위해 다양한 유틸을 제공합니다.',
                        keywords: ['utilities'],
                    },
                    items: [
                        'guides/util/common',
                        'guides/util/backend',
                    ],
                },
            ],
        },
        'Development Environment',
        'Architecture',
        'Modularization',
        'Testing',
        'Contributing',
        'Resources',
        'Releases',
        'License',
    ],
};
module.exports = sidebars;