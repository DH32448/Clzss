import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'welcome',
            component: () => import('../views/Welcome.vue'),
        },
        {
            path: '/adm/clz',
            name: 'class-management',
            component: () => import('../views/adm/clz/show.vue'),
        },
        {
            path: '/adm/course',
            name: 'course-management',
            component: () => import('../views/adm/course/show.vue'),
        },
        {
            path: '/adm/head',
            name: 'update-profile',
            component: () => import('../views/adm/head/show.vue'),
        },
        {
            path: '/adm/stu',
            name: 'student-management',
            component: () => import('../views/adm/stu/show.vue'),
        },
        {
            path: '/adm/task',
            name: 'task-management',
            component: () => import('../views/adm/task/show.vue'),
        },
        {
            path: '/adm/tea',
            name: 'teacher-management',
            component: () => import('../views/adm/tea/show.vue'),
        },
    ],
});

export default router;