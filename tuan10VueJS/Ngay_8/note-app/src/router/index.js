import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/views/HomePage.vue'
import NoteView from '@/views/NoteView.vue'
import NoteDetail from '@/views/NoteDetail.vue'

const routes = [
  { path: '/', component: HomePage },
  { path: '/notes', component: NoteView },
  { path: '/notes/:id', component: NoteDetail },
]

export default createRouter({
  history: createWebHistory(),
  routes,
})

