module.exports = {
  preset: '@vue/cli-plugin-unit-jest',
  reporters: [
    'default',
    ['./node_modules/jest-html-reporter', {
      pageTitle: 'Test Report',
      outputPath: 'test-report/index.html'
    }]
  ]
}
