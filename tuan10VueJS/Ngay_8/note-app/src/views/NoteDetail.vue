<template>
  <v-container>
    <v-card>
      <v-card-title>Xem chi tiết ghi chú</v-card-title>
      <v-card-text>
        <p><strong>Tiêu đề:</strong> {{ note.title }}</p>
        <p><strong>Nội dung:</strong> {{ note.content }}</p>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script setup>
import { useRoute } from 'vue-router';
import { useNotes } from '@/composables/useNotes';

const route = useRoute();
const { notes } = useNotes();
const note = notes.find(n => n.id == route.params.id) || { title: '', content: '' };
</script>
