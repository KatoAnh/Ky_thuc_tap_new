<template>
  <v-form @submit.prevent="onSubmit">
    <v-text-field v-model="title" label="Tiêu đề" required />
    <v-textarea v-model="content" label="Nội dung" required />
    <v-btn type="submit" color="primary">{{ editingId ? 'Cập nhật' : 'Thêm' }}</v-btn>
  </v-form>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useNotes } from '@/composables/useNotes';

const props = defineProps(['editingNote']);
const emit = defineEmits(['done']);

const title = ref('');
const content = ref('');
const editingId = ref(null);

watch(() => props.editingNote, (note) => {
  if (note) {
    title.value = note.title;
    content.value = note.content;
    editingId.value = note.id;
  }
});

const { addNote, updateNote } = useNotes();

const onSubmit = () => {
  if (editingId.value) {
    updateNote(editingId.value, { title: title.value, content: content.value });
  } else {
    addNote({ title: title.value, content: content.value });
  }
  title.value = '';
  content.value = '';
  editingId.value = null;
  emit('done');
};
</script>