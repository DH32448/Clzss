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
        {
            path: '/stu/course',
            name: 'student-course',
            component: () => import('../views/student/course.vue'),
        },
        {
            path: '/stu/pwd',
            name: 'student-password',
            component: () => import('../views/student/password.vue'),
        },
        {
            path: '/stu/mark',
            name: 'student-score',
            component: () => import('../views/student/score.vue'),
        },
        {
            path: '/tea/pwd',
            name: 'teacher-password',
            component: () => import('../views/teacher/password.vue'),
        },
        {
            path: '/tea/mark',
            name: 'teacher-mark',
            component: () => import('../views/teacher/score.vue'),
        },
    ],
});

export default router;
