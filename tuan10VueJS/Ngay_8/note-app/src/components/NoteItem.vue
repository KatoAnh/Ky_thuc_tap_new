<template>
  <v-card
    class="pa-4"
    elevation="3"
    color="blue-lighten-5"
    rounded="lg"
    style="min-height: 150px; display: flex; flex-direction: column; justify-content: space-between"
  >
    <div>
      <h3 class="text-h6 font-weight-medium mb-2">{{ note.title }}</h3>
      <div class="text-body-2">{{ note.content }}</div>
    </div>
    <v-card-actions class="justify-end mt-4">
      <v-btn icon @click="$emit('edit', note)" :aria-label="`Sửa ghi chú ${note.title}`">
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
      <v-btn icon @click="$emit('delete', note.id)" :aria-label="`Xoá ghi chú ${note.title}`">
        <v-icon color="red">mdi-delete</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup>
defineProps(['note']);
defineEmits(['edit', 'delete']);
</script>
