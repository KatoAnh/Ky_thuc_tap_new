<template>
  <v-container class="py-8">
    <v-row justify="center" align="center" class="mb-6">
      <v-col cols="12" md="8" class="text-center">
        <v-icon size="36" color="primary" class="mr-2">mdi-note-text</v-icon>
        <h2 class="text-h5 font-weight-medium d-inline-block">Ghi chú của bạn</h2>
      </v-col>
    </v-row>

    <v-row justify="center" class="mb-6">
      <v-col cols="12" md="8">
        <NoteForm :editingNote="editingNote" @done="editingNote = null" />
      </v-col>
    </v-row>

    <v-row justify="center">
      <v-col cols="12" md="10">
        <NoteList :notes="notes" @delete="deleteNote" @edit="editingNote = $event" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import NoteForm from '@/components/NoteForm.vue';
import NoteList from '@/components/NoteList.vue';
import { useNotes } from '@/composables/useNotes';
import { ref } from 'vue';

const { notes, deleteNote } = useNotes();
const editingNote = ref(null);
</script>
